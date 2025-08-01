package com.hnjee.jemilog.tag;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.tag.dto.TagCreateRequest;
import com.hnjee.jemilog.tagtheme.TagTheme;
import com.hnjee.jemilog.theme.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("is_deleted = false")
public class Tag extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    private String name;
    private String imageUrl;

    @OneToMany(mappedBy = "tag") // cascade = CascadeType.ALL
    private List<TagTheme> tagThemes = new ArrayList<>();

    public Tag(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static Tag of(TagCreateRequest request) {
        return new Tag(
                request.getName(),
                request.getImageUrl()
        );
    }
}
