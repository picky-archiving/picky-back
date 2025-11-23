package com.example.pickyback.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger UI: http://localhost:8080/swagger-ui/index.html
 * OpenAPI JSON: http://localhost:8080/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

	private static final String SECURITY_SCHEME_NAME = "X-USER-ID";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(apiInfo())
				.servers(servers())
				.tags(tags())
				.components(securityComponents())
				.addSecurityItem(new
						SecurityRequirement().addList(SECURITY_SCHEME_NAME));
	}

	private Info apiInfo() {
		return new Info()
				.title("Picky API")
				.description("""
                          ## 개요
                          청년 정책 아카이빙 서비스 **Picky**의 백엔드 REST API 문서입니다.

                          ## 인증
                          - 요청 헤더에 `X-USER-ID`를 포함하여 사용자를 식별합니다.
                          - 헤더가 없는 경우 기본값(userId=1)이 적용됩니다.

                          ## 주요 기능
                          - **정책 조회**: 카테고리별, 소득 수준별 정책 필터링 및 페이지네이션
                          - **북마크 관리**: 관심 정책 저장 및 조회
                          - **사용자 관리**: 사용자 정보 조회 및 수정
                          - **홈 화면**: 사용자 맞춤 정책 추천

                          ## 응답 형식
                          모든 API 응답은 `ApiResponse<T>` 형식으로 래핑됩니다:
                          ```json
                          {
                            "success": true,
                            "data": { ... },
                            "error": null
                          }
                          ```
                          """)
				.version("v1.0.0")
				.contact(new Contact()
						.name("Picky Development Team")
						.url("https://github.com/picky-archiving/picky-back")
						.email("picky-team@example.com"))
				.license(new License()
						.name("MIT License")
						.url("https://opensource.org/licenses/MIT"));
	}

	private List<Server> servers() {
		return List.of(
				new Server()
						.url("http://54.180.92.121:8080")
						.description("운영 환경 서버")
		);
	}

	private List<Tag> tags() {
		return List.of(
				new Tag()
						.name("Home")
						.description("홈 화면 관련 API - 사용자 맞춤 정책 추천 및 카테고리별 인기 정책 조회"),
		new Tag()
				.name("Policy")
				.description("정책 조회 API - 전체 정책 목록 조회, 카테고리별 필터링, 정렬 기능 제공"),
		new Tag()
				.name("Policy Detail")
				.description("정책 상세 조회 API - 개별 정책의 상세 정보 조회 및 조회수 증가 처리"),
		new Tag()
				.name("Bookmark")
				.description("북마크 관리 API - 관심 정책 저장, 삭제, 목록 조회 기능"),
		new Tag()
				.name("User")
				.description("사용자 관리 API - 사용자 정보 조회 및 수정 기능"),
		new Tag()
				.name("System")
				.description("시스템 API - 서버 상태 확인 및 테스트용 엔드포인트")
				);
	}

	private Components securityComponents() {
		return new Components()
				.addSecuritySchemes(SECURITY_SCHEME_NAME,
						new SecurityScheme()
								.type(SecurityScheme.Type.APIKEY)
								.in(SecurityScheme.In.HEADER)
								.name("X-USER-ID")
								.description("사용자 식별을 위한 User ID (Long 타입). 미입력 시 기본값 1이 적용됩니다."));
	}
}