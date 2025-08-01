package com.hnjee.jemilog.thememission;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.theme.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ThemeMission extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="theme_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="theme_id")
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    public ThemeMission(Theme theme, Mission mission) {
        this.theme = theme;
        this.mission = mission;
    }
}
