package com.hnjee.jemilog.mission.dto.response;

import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.theme.dto.ThemeSummaryDTO;
import lombok.*;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MissionDetailResponse {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private String colorCode;
    private List<ThemeSummaryDTO> themes;


    public static MissionDetailResponse from(Mission mission) {
        return MissionDetailResponse.builder()
                .id(mission.getId())
                .name(mission.getName())
                .description(mission.getDescription())
                .imageUrl(mission.getImageUrl())
                .colorCode(mission.getColorCode())
                .build();
    }

}
