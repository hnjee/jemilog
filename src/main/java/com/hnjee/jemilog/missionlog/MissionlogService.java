package com.hnjee.jemilog.missionlog;

import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.member.MemberRepository;
import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.mission.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionlogService {
    private final MissionlogRepository missionlogRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Missionlog missionlog) {
        validateMissionlog(missionlog.getMember(), missionlog.getMission(), missionlog.getVisitedAt());
        Missionlog saved = missionlogRepository.save(missionlog);
        return saved.getId();
    }

    private void validateMissionlog(Member member, Mission mission, LocalDateTime visitedAt) {
        // 같은 멤버 + 같은 미션 + 같은 날짜의 작성된 로그가 있으면 확인
        Optional<Missionlog> found = missionlogRepository.findByMemberAndMissionAndVisitedAt(member, mission, visitedAt);
        if(found.isPresent()){
            throw new IllegalStateException("이미 같은 날짜에 작성된 미션 로그가 있습니다.");
        }
    }


    @Transactional
    public void update(Long id, LocalDateTime visitedAt, String location, String contents, String imageUrl) {
        Missionlog missionlog = findById(id);
        missionlog.update(visitedAt, location, contents, imageUrl);
    }

    @Transactional
    public void delete(Long id) {
        Missionlog missionlog = findById(id);
        missionlog.delete();
    }

    public Missionlog findById(Long id) {
        return missionlogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션로그가 존재하지 않습니다. id=" + id));
    }

    public List<Missionlog> findAll() {
        return missionlogRepository.findAll();
    }

    public List<Missionlog> findByLocation(String location) {
        return missionlogRepository.findByLocation(location);
    }

    public List<Missionlog> findByMember(Member member) {
        return missionlogRepository.findByMember(member);
    }

    public List<Missionlog> findByMission(Mission mission) {
        return missionlogRepository.findByMission(mission);
    }

}
