package com.hnjee.jemilog.mission;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.mission.dto.request.MissionCreateRequest;
import com.hnjee.jemilog.thememission.ThemeMission;
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
public class Mission extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="mission_id")
    private Long id;

    private String name;
    private String description;
    private String contents;
    private String imageUrl;
    private String colorCode;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<ThemeMission> themeMissions = new ArrayList<>();

    public Mission(String name, String description, String contents, String imageUrl, String colorCode) {
        this.name = name;
        this.description = description;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.colorCode = colorCode;
        //themeMissions 필드 초기화됨 (builder에선 안됨)
    }

    public void update(String name, String description, String contents, String imageUrl, String colorCode) {
        this.name = name;
        this.description = description;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.colorCode = colorCode;
    }

    public static Mission of(MissionCreateRequest request) {
        return new Mission(
                request.getName(),
                request.getDescription(),
                request.getContents(),
                request.getImageUrl(),
                request.getColorCode()
        );
    }
}
