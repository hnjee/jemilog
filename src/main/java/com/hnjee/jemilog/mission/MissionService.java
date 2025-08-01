package com.hnjee.jemilog.mission;

import com.hnjee.jemilog.mission.dto.request.MissionCreateRequest;
import com.hnjee.jemilog.mission.dto.response.MissionDetailResponse;
import com.hnjee.jemilog.mission.dto.response.MissionListResponse;
import com.hnjee.jemilog.mission.dto.response.MissionSummaryDTO;
import com.hnjee.jemilog.theme.Theme;
import com.hnjee.jemilog.theme.ThemeService;
import com.hnjee.jemilog.thememission.ThemeMission;
import com.hnjee.jemilog.thememission.ThemeMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final ThemeService themeService;
    private final ThemeMissionService themeMissionService;


    private void addThemeMissions(List<Long> themeIds, Mission mission) {
        for(Long themeId: themeIds){
            Theme theme = themeService.getTheme(themeId);
            //TagTheme 생성 -> 양방향 연관관계 세팅
            themeMissionService.createAndAttach(theme, mission);
        }
    }

    private void removeThemeMissions(Mission mission) {
        List<ThemeMission> ThemeMissionsToDelete = new ArrayList<>(mission.getThemeMissions());
        for (ThemeMission themeMission : ThemeMissionsToDelete) {
            themeMissionService.deleteAndDetach(themeMission);
        }
    }

    @Transactional
    public Long createMission(MissionCreateRequest request) {
        Mission mission = Mission.of(request);
        addThemeMissions(request.getThemeIds(), mission);
        missionRepository.save(mission);
        return mission.getId();
    }

    @Transactional
    public void updateMission(Long id, String name, String description, String contents, String imageUrl, String colorCode) {
        Mission mission = getMission(id); // 예외 처리 포함
        mission.update(name, description, contents, imageUrl, colorCode);
    }

    @Transactional
    public void deleteMission(Long id) {
        Mission mission = getMission(id);
        removeThemeMissions(mission);
        mission.delete();
    }


    public Mission getMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 존재하지 않습니다. id=" + id));
    }
    public MissionDetailResponse getMissionDetailResponse(Long id) {
        Mission mission = getMission(id);
        return MissionDetailResponse.from(mission);
    }

    public MissionListResponse getMissions() {
        List<Mission> missions = missionRepository.findAll();
        return convertToMissionListResponse(missions);
    }

    public MissionListResponse getMissionsByKeyword(String keyword) {
        List<Mission> missions = missionRepository.searchByKeyword(keyword);
        return convertToMissionListResponse(missions);
    }

    private MissionListResponse convertToMissionListResponse(List<Mission> missions) {
        MissionListResponse response = new MissionListResponse();
        for (Mission mission : missions) {
            response.addMission(MissionSummaryDTO.from(mission));
        }
        return response;
    }
}
