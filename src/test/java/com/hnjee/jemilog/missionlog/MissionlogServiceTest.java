package com.hnjee.jemilog.missionlog;

import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.member.MemberRepository;
import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.mission.MissionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@DisplayName("MissionlogService 통합 테스트")
class MissionlogServiceTest {

    @Autowired MissionlogService missionlogService;
    @Autowired MissionlogRepository missionlogRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired MissionRepository missionRepository;
    @PersistenceContext EntityManager em;

    private Member createMember() {
        Member member = new Member("user1", "pw", "이현지", "01012345678", "hyunji@email.com", "현지", "2000-01-01", "image");
        memberRepository.save(member);
        return member;
    }

    private Mission createMission() {
        Mission mission = new Mission("제주 바다", "해변 미션", "내용", "url", "#00FFFF");
        return missionRepository.save(mission);
    }


    @Test
    @DisplayName("미션로그 저장 성공")
    void saveMissionlog() {
        // given
        Member member = createMember();
        Mission mission = createMission();

        LocalDateTime visitedAt = LocalDateTime.of(2025, 7, 8, 10, 0);
        Missionlog missionlog = new Missionlog(visitedAt, "제주도", "해녀체험", "url");

        // when
        Long id = missionlogService.save(missionlog, member, mission);

        // then
        Missionlog saved = missionlogService.findById(id);
        assertEquals("제주도", saved.getLocation());
        assertEquals("해녀체험", saved.getContents());
        assertEquals(member.getId(), saved.getMember().getId());
        assertEquals(mission.getId(), saved.getMission().getId());
    }

    @Test
    @DisplayName("중복 미션로그 저장 시 예외 발생")
    void duplicateMissionlog_throwsException() {
        // given
        Member member = createMember();
        Mission mission = createMission();
        LocalDateTime visitedAt = LocalDateTime.of(2025, 7, 8, 10, 0);
        Missionlog missionlog1 = new Missionlog(visitedAt, "제주도", "내용1", "url1");
        Missionlog missionlog2 = new Missionlog(visitedAt, "제주도", "내용2", "url2");

        // when
        missionlogService.save(missionlog1, member, mission);

        // then
        assertThrows(IllegalStateException.class, () -> {
            missionlogService.save(missionlog2, member, mission);
        });
    }

    @Test
    @Commit
    @DisplayName("미션로그 수정")
    void updateMissionlog() {
        // given
        Member member = createMember();
        Mission mission = createMission();
        Missionlog missionlog = new Missionlog(LocalDateTime.of(2025, 7, 8, 10, 0), "제주도", "해녀체험", "url");
        Long id = missionlogService.save(missionlog, member, mission);

        // when
        missionlogService.update(id, LocalDateTime.of(2025, 7, 9, 14, 0), "부산", "바닷가 체험", "new-url");

        // then
        Missionlog updated = missionlogService.findById(id);
        assertEquals("부산", updated.getLocation());
        assertEquals("바닷가 체험", updated.getContents());
        assertEquals("new-url", updated.getImageUrl());
        assertEquals(LocalDateTime.of(2025, 7, 9, 14, 0), updated.getVisitedAt());
    }

    @Test
    @Commit
    @DisplayName("미션로그 삭제")
    void deleteMissionlog() {
        // given
        Member member = createMember();
        Mission mission = createMission();
        Missionlog missionlog = new Missionlog(LocalDateTime.of(2025, 7, 8, 10, 0), "제주도", "내용", "url");
        Long id = missionlogService.save(missionlog, member, mission);

        // when
        missionlogService.delete(id);

        // then
        assertTrue(missionlog.isDeleted());

        List<Missionlog> all = missionlogService.findAll();
        assertTrue(all.isEmpty()); // soft delete된 로그는 제외되어야 함

    }

    @Test
    @DisplayName("전체 미션로그 조회")
    void findAllMissionlogs() {
        // given
        Member member = createMember();
        Mission mission = createMission();
        missionlogService.save(new Missionlog(LocalDateTime.now(), "서울", "내용1", "url1"), member, mission);
        missionlogService.save(new Missionlog(LocalDateTime.now(), "부산", "내용2", "url2"), member, mission);

        // when
        List<Missionlog> all = missionlogService.findAll();

        // then
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("장소 기준으로 미션로그 조회")
    void findByLocation() {
        // given
        Member member = createMember();
        Mission mission = createMission();
        missionlogService.save(new Missionlog(LocalDateTime.now(), "제주", "내용1", "url1"), member, mission);
        missionlogService.save(new Missionlog(LocalDateTime.now(), "부산", "내용2", "url2"), member, mission);

        // when
        List<Missionlog> jejuLogs = missionlogService.findByLocation("제주");

        // then
        assertEquals(1, jejuLogs.size());
        assertEquals("제주", jejuLogs.get(0).getLocation());
    }
}
