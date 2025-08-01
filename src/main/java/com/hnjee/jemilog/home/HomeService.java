package com.hnjee.jemilog.home;

import com.hnjee.jemilog.mission.MissionService;
import com.hnjee.jemilog.theme.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final MissionService missionService;
    private final ThemeService themeService;

    //public HomeResponse getHomeContent() {
        //List<MissionDto> popularMissions = missionService.getPopularMissions();
        //List<MissionDto> recommendedMissions = missionService.getRecommendedMissions();
        //List<ThemeDto> todayThemes = themeService.getTodayThemes();

        //return new HomeResponse(popularMissions, recommendedMissions, todayThemes);
    //}
}