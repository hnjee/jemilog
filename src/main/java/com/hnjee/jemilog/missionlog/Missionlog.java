package com.hnjee.jemilog.missionlog;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.mission.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("is_deleted = false")
public class Missionlog extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="missionlog_id")
    private Long id;

    private LocalDateTime visitedAt;
    private String location;
    private String contents;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public Missionlog(LocalDateTime visitedAt, String location, String contents, String imageUrl, Mission mission, Member member) {
        this.visitedAt = visitedAt;
        this.location = location;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.mission = mission;
        this.member = member;
    }

    public void update(LocalDateTime visitedAt, String location, String contents, String imageUrl) {
        this.visitedAt = visitedAt;
        this.location = location;
        this.contents = contents;
        this.imageUrl = imageUrl;
    }

}
