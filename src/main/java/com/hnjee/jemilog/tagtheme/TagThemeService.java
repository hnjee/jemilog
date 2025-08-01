package com.hnjee.jemilog.tagtheme;

import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.theme.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagThemeService {
    private final TagThemeRepository tagThemeRepository;

    // 1. TagTheme 생성 및 양방향 관계 설정
    public void createAndAttach(Tag tag, Theme theme) {
        TagTheme tagTheme = new TagTheme(tag, theme);
        tag.getTagThemes().add(tagTheme);
        theme.getTagThemes().add(tagTheme);
        tagThemeRepository.save(tagTheme);
    }

    // 2. TagTheme 삭제 및 양방향 관계 해제
    public void deleteAndDetach(TagTheme tagTheme) {
        tagTheme.getTag().getTagThemes().remove(tagTheme);
        tagTheme.getTheme().getTagThemes().remove(tagTheme);
        tagThemeRepository.delete(tagTheme);
    }
}
