package com.hnjee.jemilog.tag.dto;

import com.hnjee.jemilog.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TagResponse {
    private Long id;
    private String name;
    private String imageUrl;

    public static TagResponse from(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .imageUrl(tag.getImageUrl())
                .build();
    }
}
