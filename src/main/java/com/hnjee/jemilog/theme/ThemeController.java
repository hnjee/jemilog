package com.hnjee.jemilog.theme;

import com.hnjee.jemilog.common.dto.ApiResponse;
import com.hnjee.jemilog.theme.dto.ThemeCreateRequest;
import com.hnjee.jemilog.theme.dto.ThemeResponse;
import com.hnjee.jemilog.theme.dto.ThemeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    // 1. 테마 전체 목록 조회
    @GetMapping
    public ApiResponse<List<ThemeResponse>> getThemes() {
        List<ThemeResponse> themes = themeService.getThemeResponses();
        return ApiResponse.ok(themes);
    }

    // 2. 테마 단건 조회
    @GetMapping("/{themeId}")
    public ApiResponse<ThemeResponse> getTheme(@PathVariable Long themeId) {
        ThemeResponse response = themeService.getThemeResponse(themeId);
        return ApiResponse.ok(response);
    }

    // 3. 테마 등록
    @PostMapping
    public ApiResponse<Long> createTheme(@RequestBody ThemeCreateRequest request) {
        Long themeId = themeService.createTheme(request);
        return ApiResponse.ok(themeId, "테마가 등록되었습니다.");
    }

    // 4. 테마 수정
    @PutMapping("/{themeId}")
    public ApiResponse<Void> updateTheme(@PathVariable Long themeId, @RequestBody ThemeUpdateRequest request) {
        themeService.updateTheme(themeId, request);
        return ApiResponse.ok(null, "테마가 수정되었습니다.");
    }

    // 5. 테마 삭제
    @DeleteMapping("/{themeId}")
    public ApiResponse<Void> deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ApiResponse.ok(null, "테마가 삭제되었습니다.");
    }
}
