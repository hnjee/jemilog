package com.hnjee.jemilog.recommend;

import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.mission.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {
    private final RecommendRepository recommendRepository;
    private final MissionRepository missionRepository;
    // 추천 저장
    @Transactional
    public Long save(Recommend recommend) {
        recommendRepository.save(recommend);
        return recommend.getId();
    }

    // 추천 단건 조회
    public Recommend findById(Long id) {
        return recommendRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 추천이 존재하지 않습니다. id=" + id));
    }

    // 미션 기준 조회
    public List<Recommend> findByMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 존재하지 않습니다. id=" + missionId));
        return recommendRepository.findByMission(mission);
    }

    // 이름 기준 검색
    public List<Recommend> searchByName(String name) {
        return recommendRepository.findByName(name);
    }

    // 지역 기준 검색
    public List<Recommend> searchByLocation(String location) {
        return recommendRepository.findByLocation(location);
    }

    // 수정
    @Transactional
    public void update(Long id, String infoUrl, String location, String description, String name) {
        Recommend recommend = findById(id);
        recommend.update(infoUrl, location, description, name);
    }

    // 삭제 (soft delete)
    @Transactional
    public void delete(Long id) {
        Recommend recommend = findById(id);
        recommend.delete(); // BaseEntity의 is_deleted = true 처리
    }
}