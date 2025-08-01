package com.hnjee.jemilog.bookmark;

import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.thememission.ThemeMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByMember(Member member);
    Optional<Bookmark> findByMemberAndMission(Member member, Mission mission);
}