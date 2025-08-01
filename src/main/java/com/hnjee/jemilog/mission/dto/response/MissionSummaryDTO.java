package com.hnjee.jemilog.mission.dto.response;

import com.hnjee.jemilog.mission.Mission;
import com.hnjee.jemilog.theme.Theme;
import com.hnjee.jemilog.theme.dto.ThemeResponse;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MissionSummaryDTO {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private String colorCode;

    public static MissionSummaryDTO from(Mission mission) {
        return MissionSummaryDTO.builder()
                .id(mission.getId())
                .name(mission.getName())
                .description(mission.getDescription())
                .imageUrl(mission.getImageUrl())
                .colorCode(mission.getColorCode())
                .build();
    }
}
