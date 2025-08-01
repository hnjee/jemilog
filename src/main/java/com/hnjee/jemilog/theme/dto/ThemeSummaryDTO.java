package com.hnjee.jemilog.theme.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeSummaryDTO {
    private Long id;
    private String name;
}
