package com.example.pickyback.domain.policy.controller;

import com.example.pickyback.domain.policy.dto.SinglePolicyResponseDto;
import com.example.pickyback.domain.policy.service.SinglePolicyService;
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
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(
        name = "Policy Detail API",
        description = "정책 상세 조회 API - 개별 정책의 상세 정보 조회 및 조회수 증가 처리"
)
public class SinglePolicyApiController {

    private final SinglePolicyService singlePolicyService;

    @Operation(
            summary = "정책 상세 정보 조회",
            description = "지정된 정책의 상세 정보를 조회합니다. 이 API를 호출하면 해당 정책의 조회수가 자동으로 1 증가합니다. "
                    + "반환되는 정보에는 정책명, 상세 설명, 지원 대상, 지원 내용, 신청 방법, 신청 기간, 담당 기관 정보 등이 포함됩니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "정책 상세 정보 조회 성공. 조회수가 1 증가됩니다.",
                    content = @Content(schema = @Schema(implementation = SinglePolicyResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "정책 ID가 유효한 숫자 형식이 아닌 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 ID의 정책을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류가 발생한 경우",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @GetMapping("/{policyId}")
    public ApiResponse<SinglePolicyResponseDto> getSinglePolicy(
            @Parameter(
                    description = "조회할 정책의 고유 식별자",
                    required = true,
                    example = "1"
            )
            @PathVariable String policyId
    ) {
        SinglePolicyResponseDto response =
                singlePolicyService.increaseViewAndGetPolicy(Long.parseLong(policyId));

        return ApiResponse.success(response);
    }
}
