package com.hnjee.jemilog.mission;

import com.hnjee.jemilog.theme.Theme;
import com.hnjee.jemilog.theme.ThemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("MissionService 통합 테스트")
class MissionServiceTest {

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private ThemeRepository themeRepository;

    private Mission createSampleMission() {
        return new Mission(
                "미션 이름",
                "설명",
                "내용",
                "http://image.com/sample.jpg",
                "#FFCC00"
        );
    }

    private List<Theme> createSampleThemes() {
        Theme theme1 = new Theme("자연", "자연 테마", "http://image.com/sample.jpg", "#00FF00");
        Theme theme2 = new Theme("도시", "도시 테마", "http://image.com/sample.jpg", "#0000FF");
        return List.of(themeRepository.save(theme1), themeRepository.save(theme2));
    }

    @Test
    @DisplayName("미션 저장 + 테마 연결")
    void saveMissionWithThemes() {
        // given
        Mission mission = createSampleMission();
        List<Theme> themes = createSampleThemes();

        // when
        Long id = missionService.save(mission, themes);

        // then
        Mission saved = missionService.findById(id);
        assertEquals(mission.getName(), saved.getName());
        assertEquals(2, saved.getThemeMissions().size()); // 연결된 ThemeMission 수
    }

    @Test
    @DisplayName("미션 단건 조회")
    void findById() {
        Mission mission = createSampleMission();
        Long id = missionService.save(mission, createSampleThemes());

        Mission found = missionService.findById(id);

        assertNotNull(found);
        assertEquals("미션 이름", found.getName());
    }

    @Test
    @DisplayName("미션 전체 조회")
    void findAll() {
        missionService.save(createSampleMission(), createSampleThemes());
        missionService.save(new Mission("두번째", "desc", "cont", "img", "#111111"), createSampleThemes());

        List<Mission> all = missionService.findAll();

        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("미션 수정")
    void updateMission() {
        Mission mission = createSampleMission();
        Long id = missionService.save(mission, createSampleThemes());

        missionService.updateMission(id, "새 이름", "새 설명", "새 내용", "new_url", "#000000");

        Mission updated = missionService.findById(id);
        assertEquals("새 이름", updated.getName());
        assertEquals("새 설명", updated.getDescription());
    }

    @Test
    @DisplayName("미션 삭제")
    void deleteMission() {
        Mission mission = createSampleMission();
        Long id = missionService.save(mission, createSampleThemes());

        missionService.deleteMission(id);

        assertThrows(IllegalArgumentException.class, () -> missionService.findById(id));
    }

    @Test
    @DisplayName("키워드로 미션 검색")
    void searchMissions() {
        Mission m1 = new Mission("제주도 미션", "제주 여행", "내용", "url", "#000");
        Mission m2 = new Mission("강릉 미션", "바다", "내용", "url", "#000");
        missionService.save(m1, createSampleThemes());
        missionService.save(m2, createSampleThemes());

        List<Mission> result = missionService.searchMissions("제주");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("제주"));
    }
}
