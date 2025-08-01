package com.hnjee.jemilog.thememission;


import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.theme.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeMissionService {
    private final ThemeMissionRepository themeMissionRepository;

    // 1. ThemeMission 생성 및 양방향 관계 설정
    public void createAndAttach(Theme theme, Mission mission) {
        ThemeMission themeMission = new ThemeMission(theme, mission);
        theme.getThemeMissions().add(themeMission);
        mission.getThemeMissions().add(themeMission);
        themeMissionRepository.save(themeMission);
    }

    // 2. ThemeMission 삭제 및 양방향 관계 해제
    public void deleteAndDetach(ThemeMission themeMission) {
        themeMission.getTheme().getThemeMissions().remove(themeMission);
        themeMission.getMission().getThemeMissions().remove(themeMission);
        themeMissionRepository.delete(themeMission);
    }
}
