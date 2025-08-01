package com.hnjee.jemilog.theme.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeUpdateRequest {
    private String name;
    private String description;
    private String imageUrl;
    private String colorCode;
    private List<Long> tagIds;
}
