package com.hnjee.jemilog.recommend;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.mission.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("is_deleted = false")
public class Recommend extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="recommend_id")
    private Long id;

    private String name;
    private String description;
    private String location;
    private String infoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    public Recommend(Mission mission, String infoUrl, String location, String description, String name) {
        this.mission = mission;
        this.infoUrl = infoUrl;
        this.location = location;
        this.description = description;
        this.name = name;
    }
    public void update(String infoUrl, String location, String description, String name) {
        this.infoUrl = infoUrl;
        this.location = location;
        this.description = description;
        this.name = name;
    }
}
