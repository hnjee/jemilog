package com.hnjee.jemilog.theme;

import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.tag.TagService;
import com.hnjee.jemilog.tagtheme.TagTheme;
import com.hnjee.jemilog.tagtheme.TagThemeService;
import com.hnjee.jemilog.theme.dto.ThemeCreateRequest;
import com.hnjee.jemilog.theme.dto.ThemeResponse;
import com.hnjee.jemilog.theme.dto.ThemeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final TagService tagService;
    private final TagThemeService tagThemeService;


    private void addTagThemes(List<Long> tagIds, Theme theme) {
        for(Long tagId: tagIds){
            Tag tag = tagService.getTag(tagId);
            //TagTheme 생성 -> 양방향 연관관계 세팅
            tagThemeService.createAndAttach(tag, theme);
        }
    }

    private void removeTagThemes(Theme theme) {
        List<TagTheme> tagThemesToDelete = new ArrayList<>(theme.getTagThemes());
        for (TagTheme tagTheme : tagThemesToDelete) {
            tagThemeService.deleteAndDetach(tagTheme);
        }
    }

    //theme 만들기
    @Transactional //readonly 디폴트 false
    public Long createTheme(ThemeCreateRequest request) {
        Theme theme = Theme.of(request);
        addTagThemes(request.getTagIds(), theme);
        themeRepository.save(theme);
        return theme.getId();
    }

    @Transactional
    public void updateTheme(Long themeId, ThemeUpdateRequest request) {
        //1. Theme 내용 수정
        Theme theme = getTheme(themeId);
        theme.update(
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getColorCode()
        );

        //2. TagTheme 관계 수정
        //2-1) 기존 TagTheme 관계 제거
        removeTagThemes(theme);
        //2-2) 새롭게 Tag 리스트 추가
        addTagThemes(request.getTagIds(), theme);
    }

    @Transactional
    public void deleteTheme(Long id) {
        Theme theme = getTheme(id);
        //1. 기존 tagThemes 관계 제거
        removeTagThemes(theme);
        //2. theme 삭제
        theme.delete(); // 도메인 내부에서 값 변경
    }

    //테마 아이디로 조회
    public Theme getTheme(long id){
        return themeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 테마가 없습니다."));
    }

    //테마 목록 조회
    public List<ThemeResponse> getThemeResponses() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponse::from)
                .toList();
    }

    //테마 아이디로 조회 (클라이언트 응답용)
    public ThemeResponse getThemeResponse(long id){
        Theme theme = getTheme(id);
        return ThemeResponse.from(theme);
    }

    //테마 이름으로 조회
    public Theme getThemeByName(String name) {
        return themeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 테마가 없습니다."));
    }
}
