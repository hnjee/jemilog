package com.hnjee.jemilog.theme.dto;

import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.theme.Theme;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String colorCode;

    public static ThemeResponse from(Theme theme) {
        return ThemeResponse.builder()
                .id(theme.getId())
                .name(theme.getName())
                .description(theme.getDescription())
                .imageUrl(theme.getImageUrl())
                .colorCode(theme.getColorCode())
                .build();
    }
}
