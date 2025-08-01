package com.hnjee.jemilog.tag;

import com.hnjee.jemilog.common.dto.ApiResponse;
import com.hnjee.jemilog.tag.dto.TagCreateRequest;
import com.hnjee.jemilog.tag.dto.TagResponse;
import com.hnjee.jemilog.tag.dto.TagUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    // 1. 태그 목록 조회
    @GetMapping
    public ApiResponse<List<TagResponse>> getTags() {
        List<TagResponse> tags = tagService.getTags();
        return ApiResponse.ok(tags); //message: 요청 성공
    }

    @GetMapping("/{tagId}")
    public ApiResponse<TagResponse> getTag(@PathVariable  Long tagId) {
        TagResponse tag = tagService.getTagResponse(tagId);
        return ApiResponse.ok(tag); //message: 요청 성공
    }

    // 2. 태그 등록
    @PostMapping
    public ApiResponse<Long> createTag(@RequestBody TagCreateRequest request) {
        Long tagId = tagService.createTag(request);
        return ApiResponse.ok(tagId, "태그가 등록되었습니다.");
    }

    // 3. 태그 수정
    @PutMapping("/{tagId}")
    public ApiResponse<Void> updateTag(
            @PathVariable Long tagId,
            @RequestBody TagUpdateRequest request) {
        tagService.updateTag(tagId, request);
        return ApiResponse.ok(null, "태그가 수정되었습니다.");
    }

    // 4. 태그 삭제
    @DeleteMapping("/{tagId}")
    public ApiResponse<Long> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.ok(tagId, "태그가 삭제되었습니다.");
    }

}
