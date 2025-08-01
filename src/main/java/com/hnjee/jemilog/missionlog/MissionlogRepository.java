package com.hnjee.jemilog.missionlog;

import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.mission.Mission;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MissionlogRepository extends JpaRepository<Missionlog, Long> {
    List<Missionlog> findByMember(Member member);
    List<Missionlog> findByMission(Mission mission);
    List<Missionlog> findByLocation(String location);
    Optional<Missionlog> findByMemberAndMissionAndVisitedAt(Member member, Mission mission, LocalDateTime visitedAt);
}