package com.hnjee.jemilog.mission;

import com.hnjee.jemilog.mission.dto.request.MissionCreateRequest;
import com.hnjee.jemilog.mission.dto.request.MissionUpdateRequest;
import com.hnjee.jemilog.mission.dto.response.MissionDetailResponse;
import com.hnjee.jemilog.mission.dto.response.MissionListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 1. 미션 생성
    @PostMapping
    public ResponseEntity<Long> createMission(@RequestBody @Valid MissionCreateRequest request) {
        Long missionId = missionService.createMission(request);
        return ResponseEntity.ok(missionId);
    }

    // 2. 미션 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMission(@PathVariable Long id,
                                              @RequestBody @Valid MissionUpdateRequest request) {
        missionService.updateMission(id, request.getName(), request.getDescription(),
                request.getContents(), request.getImageUrl(), request.getColorCode());
        return ResponseEntity.noContent().build();
    }

    // 3. 미션 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        missionService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }

    // 4. 미션 단건 조회 (상세)
    @GetMapping("/{id}")
    public ResponseEntity<MissionDetailResponse> getMission(@PathVariable Long id) {
        MissionDetailResponse response = missionService.getMissionDetailResponse(id);
        return ResponseEntity.ok(response);
    }

    // 5. 미션 전체 조회
    @GetMapping
    public ResponseEntity<MissionListResponse> getAllMissions(@RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(missionService.getMissionsByKeyword(keyword));
        }
        return ResponseEntity.ok(missionService.getMissions());
    }
}

