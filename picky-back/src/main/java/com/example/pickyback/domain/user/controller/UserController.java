package com.example.pickyback.domain.user.controller;

import com.example.pickyback.domain.user.dto.UserRequestDto;
import com.example.pickyback.domain.user.dto.UserResponseDto;
import com.example.pickyback.domain.user.service.UserService;
import com.example.pickyback.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "사용자 정보 조회",
            description = "지정된 사용자의 상세 정보를 조회합니다. "
                    + "반환되는 정보에는 사용자명, 생년월일, 소득 수준, 관심 카테고리, "
                    + "거주 지역 등의 프로필 정보가 포함됩니다. "
                    + "userId가 제공되지 않으면 기본 사용자(ID=1)의 정보를 반환합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "사용자 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 사용자 ID 형식인 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 ID의 사용자를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류가 발생한 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUser(
            @Parameter(
                    description = "조회할 사용자의 고유 식별자. 미입력 시 기본값 1이 적용됩니다.",
                    example = "1"
            )
            @PathVariable(required = false) Long userId
    ) {
        Long targetUserId = (userId != null) ? userId : 1L;
        return ApiResponse.success(userService.getUser(targetUserId));
    }

    @Operation(
            summary = "사용자 정보 수정",
            description = "지정된 사용자의 프로필 정보를 수정합니다. "
                    + "수정 가능한 정보에는 사용자명, 생년월일, 소득 수준, 관심 카테고리, "
                    + "거주 지역 등이 포함됩니다. 요청 본문에 포함된 필드만 업데이트되며, "
                    + "수정 완료 후 업데이트된 전체 사용자 정보를 반환합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "사용자 정보 수정 성공. 수정된 사용자 정보를 반환합니다.",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "요청 본문의 형식이 올바르지 않거나 유효성 검사에 실패한 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 ID의 사용자를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류가 발생한 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @PostMapping("/{userId}")
    public ApiResponse<UserResponseDto> updateUser(
            @Parameter(
                    description = "수정할 사용자의 고유 식별자. 미입력 시 기본값 1이 적용됩니다.",
                    example = "1"
            )
            @PathVariable(required = false) Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 사용자 정보. 수정하려는 필드만 포함하면 됩니다.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))
            )
            @RequestBody UserRequestDto userRequestDto
    ) {
        Long targetUserId = (userId != null) ? userId : 1L;
        return ApiResponse.success(userService.updateUser(targetUserId, userRequestDto));
    }
}
