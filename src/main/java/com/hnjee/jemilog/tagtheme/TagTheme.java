package com.hnjee.jemilog.tagtheme;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.theme.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TagTheme extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="tag_theme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="theme_id")
    private Theme theme;

    public TagTheme(Tag tag, Theme theme) {
        this.tag = tag;
        this.theme = theme;
    }
}
