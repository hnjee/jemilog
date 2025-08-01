package com.hnjee.jemilog.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagUpdateRequest {
    private String name;
    private String imageUrl;
}
