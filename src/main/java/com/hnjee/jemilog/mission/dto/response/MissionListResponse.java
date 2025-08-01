package com.hnjee.jemilog.mission.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class MissionListResponse {
    List<MissionSummaryDTO> missions = new ArrayList<>();
    //페이징 처리를 위해 필요한 필드들 추후 추가

    public void addMission(MissionSummaryDTO dto) {
        this.missions.add(dto);
    }
}
