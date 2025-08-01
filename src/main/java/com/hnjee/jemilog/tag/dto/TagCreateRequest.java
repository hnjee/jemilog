package com.hnjee.jemilog.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagCreateRequest {
    private String name;
    private String imageUrl;
}
