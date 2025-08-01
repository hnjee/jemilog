package com.hnjee.jemilog.theme;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.tag.dto.TagCreateRequest;
import com.hnjee.jemilog.tag.dto.TagResponse;
import com.hnjee.jemilog.tagtheme.TagTheme;
import com.hnjee.jemilog.theme.dto.ThemeCreateRequest;
import com.hnjee.jemilog.thememission.ThemeMission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = false")
public class Theme extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="theme_id")
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private String colorCode;

    @OneToMany(mappedBy = "theme") // cascade = CascadeType.ALL
    private List<TagTheme> tagThemes = new ArrayList<>();

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<ThemeMission> themeMissions = new ArrayList<>();

    public Theme(String name, String description, String imageUrl, String colorCode) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colorCode = colorCode;
//        this.tagThemes = new ArrayList<>();
//        this.themeMissions = new ArrayList<>();
    }

    public void update(String name, String description, String imageUrl, String colorCode) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colorCode = colorCode;
    }
    public static Theme of(ThemeCreateRequest request) {
        return new Theme(
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getColorCode()
        );
    }
}
