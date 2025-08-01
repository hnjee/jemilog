package com.hnjee.jemilog.tag;

import com.hnjee.jemilog.tag.dto.TagCreateRequest;
import com.hnjee.jemilog.tag.dto.TagResponse;
import com.hnjee.jemilog.tag.dto.TagUpdateRequest;
import com.hnjee.jemilog.tagtheme.TagTheme;
import com.hnjee.jemilog.tagtheme.TagThemeRepository;
import com.hnjee.jemilog.tagtheme.TagThemeService;
import com.hnjee.jemilog.theme.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagThemeService tagThemeService;

    //Tag 만들기
    @Transactional //readonly 디폴트 false
    public Long createTag(TagCreateRequest request) {
        Tag tag = Tag.of(request);
        validateDuplicateTag(tag); //중복 태그명 검증
        Tag savedTag = tagRepository.save(tag);
        return savedTag.getId();
    }

    private void validateDuplicateTag(Tag tag) {
        //EXCEPTION
        Optional<Tag> foundTag = tagRepository.findByName(tag.getName());
        if (foundTag.isPresent()) {
            throw new IllegalStateException("이미 존재하는 태그명입니다.");
        }
    }

    @Transactional
    public void updateTag(Long id, TagUpdateRequest request) {
        Tag tag = getTag(id);
        tag.update(request.getName(), request.getImageUrl()); // 도메인 내부에서 값 변경
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = getTag(id);
        //TagTheme 관계 제거
        List<TagTheme> tagThemesToDelete = new ArrayList<>(tag.getTagThemes());
        for (TagTheme tagTheme : tagThemesToDelete) {
           tagThemeService.deleteAndDetach(tagTheme);
        }
        //tag 삭제 (soft delete)
        tag.delete();
    }

    //태그 전체 리스트 조회
    public List<TagResponse> getTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagResponse::from) // 또는 new TagResponse(tag)
                .collect(Collectors.toList());
    }

    //태그 아이디로 조회
    public Tag getTag(long id){
        return tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다."));
    }

    // 태그 아이디로 조회 (클라이언트 응답용)
    public TagResponse getTagResponse(long id){
        Tag tag = getTag(id); // 위 메서드 재사용
        return TagResponse.from(tag);
    }

    //태그 이름으로 조회
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다."));
    }

}
