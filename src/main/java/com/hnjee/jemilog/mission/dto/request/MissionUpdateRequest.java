package com.hnjee.jemilog.mission.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionUpdateRequest {
    private String name;
    private String description;
    private String contents;
    private String imageUrl;
    private String colorCode;
    private List<Long> themeIds;


}
