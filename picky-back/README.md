# Picky-Back

사용자 맞춤형 정책 정보 제공 백엔드 API

## 📌 개요

소득분위 기반 정책 추천 및 북마크 기능을 제공하는 RESTful API 서버

## 🛠 기술 스택

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Data JPA**
- **MySQL**
- **Springdoc OpenAPI** (Swagger)

## 🗂 프로젝트 구조

```
src/main/java/com/example/pickyback
├── domain
│   ├── user          # 사용자 관리
│   ├── policy        # 정책 정보
│   └── bookmark      # 북마크 기능
└── global
    ├── config        # 설정
    ├── dto           # 공통 응답 형식
    ├── entity        # 공통 엔티티
    └── exception     # 예외 처리
```

## 🚀 주요 기능

### User
- 사용자 정보 조회/수정
- 소득분위 관리

### Policy
- 정책 목록 조회 (페이징)
- 정책 상세 조회
- 카테고리별 정책 조회
- 소득분위별 맞춤 정책 추천
- 조회수 증가

### Bookmark
- 북마크 추가/삭제
- 북마크된 정책 목록 조회

### Home
- 메인 화면 데이터 (소득분위별 추천 + 카테고리별 정책)

## 📡 API 엔드포인트

### User API
- `GET /api/user/{userId}` - 사용자 정보 조회
- `POST /api/user/{userId}` - 사용자 정보 수정

### Policy API
- `GET /api/post/{policyId}` - 정책 상세 조회
- `GET /api/post` - 전체 정책 목록
- `GET /api/post/bookmarked` - 북마크된 정책 목록
- `GET /api/post/income` - 소득분위별 정책 추천
- `GET /api/category/{category}` - 카테고리별 정책 목록

### Bookmark API
- `POST /api/post/{policyId}/bookmark` - 북마크 추가
- `DELETE /api/post/{policyId}/bookmark` - 북마크 삭제

### Home API
- `GET /api/home` - 홈 화면 데이터

## 🔧 설정 및 실행

### 1. 환경 설정

`src/main/resources/application.yml` 또는 `application.properties` 파일 생성

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/pickyback
    username: your-username
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 2. 실행

```bash
./gradlew bootRun
```

### 3. API 문서 확인

```
http://localhost:8080/swagger-ui/index.html
```

## 📦 빌드

```bash
./gradlew build
```

## 📝 응답 형식

### 성공 응답
```json
{
  "status": 200,
  "success": true,
  "message": "요청이 성공했습니다.",
  "data": {},
  "timestamp": "2024-01-01T10:00:00"
}
```

### 실패 응답
```json
{
  "status": 400,
  "success": false,
  "message": "에러 메시지",
  "data": null,
  "timestamp": "2024-01-01T10:00:00"
}
```
