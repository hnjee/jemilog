package com.hnjee.jemilog.tagtheme;

import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagThemeRepository extends JpaRepository<TagTheme, Long> {
    List<TagTheme> findByTagId(Long tagId);
    List<TagTheme> findByThemeId(Long themeId);
}
