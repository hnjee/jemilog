package com.hnjee.jemilog.recommend;

import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.missionlog.Missionlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    List<Recommend> findByMission(Mission mission);
    List<Recommend> findByName(String name);
    List<Recommend> findByLocation(String location);
}