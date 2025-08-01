package com.hnjee.jemilog.bookmark;

import com.hnjee.jemilog.common.entity.BaseEntity;
import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.mission.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public Bookmark(Member member, Mission mission) {
        this.member = member;
        this.mission = mission;
    }
}
