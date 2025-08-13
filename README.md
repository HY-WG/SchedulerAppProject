# Spring 숙련 일정 관리 앱 Develop

## 기반: Spring Boot + Spring Data JPA + (세션/쿠키 인증) + BCrypt(비밀번호 해시)
## Base URL 예시: http://localhost:8080

### 인증 개요

방식: 세션 기반 인증 (JSESSIONID 쿠키)

공개 엔드포인트(필터 예외): POST /users(회원가입), POST /auth/login

보호 엔드포인트: 그 외 전체. 세션 없거나 만료 시 401 Unauthorized

로그인/세션 동작

클라이언트가 POST /auth/login 호출 → 로그인 성공 시 서버가 세션 생성, JSESSIONID 쿠키 세팅

이후 요청에서 JSESSIONID 자동 전송(브라우저) 혹은 수동 쿠키 포함(클라이언트)

POST /auth/logout 호출 시 세션 무효화

### 에러 규칙

+ 400 Bad Request: 유효성 실패, 포맷 오류, 일반 런타임 예외

+ 401 Unauthorized: 로그인 필요 또는 자격 불일치(로그인 실패 포함)

+ 404 Not Found: 조회/수정/삭제 대상 리소스 없음

+ 409 Conflict: 이메일 중복 등(선택 적용)

에러 바디(예시)
``` 
{
  "status": 400,
  "message": "이메일 형식이 올바르지 않습니다."
}
``` 
### 리소스 스키마 (요약)

- User

- id: Long(PK)

- username: String(<=20)

- email: String (unique)

- password: String(BCrypt hash)

- createdAt: LocalDateTime (JPA Auditing)

- updatedAt: LocalDateTime (JPA Auditing)

- Schedule

- id: Long(PK)

- title: String(<=50)

- content: Text

- userId: Long (FK → users.id)

- createdAt, updatedAt: LocalDateTime

- Comment

- content: Text

- userId: Long (FK → users.id)

- scheduleId: Long (FK → schedules.id)

- createdAt, updatedAt: LocalDateTime

## API 상세

### 1) 인증

POST /auth/login

설명: 이메일/비밀번호로 로그인, 세션 시작

요청 바디
``` 
{
  "email": "user@example.com",
  "password": "plain-password"
}
``` 
응답: 200 OK, Body(문자열) "로그인 성공"

실패: 401 Unauthorized (이메일/비밀번호 불일치), 400 Bad Request (포맷 오류 등)

POST /auth/logout

설명: 세션 종료

요청: 바디 없음, 쿠키로 세션 식별

응답: 200 OK, Body(문자열) "로그아웃 성공"

### 2) 사용자 (회원가입/관리)

- 목록 조회, 생성, 수정, 삭제 중심. 

POST /users

설명: 회원가입 (공개)

요청 바디
``` 
{
  "username": "tester",
  "email": "tester@example.com",
  "password": "plain-password"
}
``` 
응답: 201 Created (또는 구현에 따라 200 OK), 생성된 User JSON
``` 
{
  "id": 1,
  "username": "tester",
  "email": "tester@example.com",
  "createdAt": "2025-08-12T10:00:00",
  "updatedAt": "2025-08-12T10:00:00"
}
``` 
비고: 서버는 비밀번호를 BCrypt로 해시해 저장. 

GET /users

- 설명: 사용자 목록 (보호)

응답: 200 OK, User 배열

PUT /users/{id}

설명: 사용자 수정(사용자명/이메일/비밀번호 변경)

요청 바디 (필요 필드만 전달)
``` 
{
  "username": "newName",
  "email": "new@example.com",
  "password": "new-password"
}
``` 
응답: 200 OK, 수정된 User JSON

- DELETE /users/{id}

설명: 사용자 삭제

응답: 204 No Content (또는 200 OK)

### 3) 일정 (Schedule)

-POST /schedules

설명: 일정 생성 (보호)

요청 바디
``` 
{
  "title": "회의",
  "content": "주간 회의 진행",
  "userId": 1
}
``` 
응답: 201 Created (또는 200 OK), 생성된 Schedule JSON


- GET /schedules

설명: 일정 목록 (보호)

응답: 200 OK, Schedule 배열

PUT /schedules/{id}

설명: 일정 수정 (보호)

요청 바디
``` 
{
  "title": "회의(수정)",
  "content": "안건 업데이트",
  "userId": 1
}
``` 
응답: 200 OK, 수정된 Schedule JSON

- DELETE /schedules/{id}

설명: 일정 삭제 (보호)

응답: 204 No Content (또는 200 OK)

### 4) 댓글 (Comment)

- POST /comments

설명: 댓글 생성 (보호)

요청 바디
``` 
{
  "content": "좋은 일정이네요!",
  "userId": 1,
  "scheduleId": 2
}
``` 
응답: 201 Created (또는 200 OK), CommentResponseDto
``` 
{
  "id": 5,
  "content": "좋은 일정이네요!",
  "userId": 1,
  "scheduleId": 2,
  "createdAt": "2025-08-12T10:10:00",
  "updatedAt": "2025-08-12T10:10:00"
}
```

- GET /comments/schedule/{scheduleId}

설명: 특정 일정의 댓글 목록 (보호)

응답: 200 OK, CommentResponseDto 배열

- PUT /comments/{id}

설명: 댓글 수정 (보호)

요청 바디
``` 
{
  "content": "내용 수정",
  "userId": 1,
  "scheduleId": 2
}
``` 
응답: 200 OK, 수정된 CommentResponseDto

- DELETE /comments/{id}

설명: 댓글 삭제 (보호)

응답: 204 No Content (또는 200 OK)


# ERD

erDiagram
    USERS ||--o{ SCHEDULES : "writes"
    USERS ||--o{ COMMENTS  : "writes"
    SCHEDULES ||--o{ COMMENTS : "has"

    USERS {
        BIGINT id PK
        VARCHAR username
        VARCHAR email UNIQUE
        VARCHAR password
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    SCHEDULES {
        BIGINT id PK
        VARCHAR title
        TEXT content
        BIGINT user_id FK
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    COMMENTS {
        BIGINT id PK
        TEXT content
        BIGINT user_id FK
        BIGINT schedule_id FK
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

+ 테이블/컬럼 정의 (상세)

users

id BIGINT AUTO_INCREMENT PRIMARY KEY

username VARCHAR(20) NOT NULL

email VARCHAR(255) NOT NULL UNIQUE

password VARCHAR(100) NOT NULL (BCrypt 해시 길이 고려하여 60~100 권장)

created_at TIMESTAMP NOT NULL (JPA Auditing)

updated_at TIMESTAMP NOT NULL (JPA Auditing)

schedules

id BIGINT AUTO_INCREMENT PRIMARY KEY

title VARCHAR(50) NOT NULL

content TEXT NOT NULL

user_id BIGINT NOT NULL REFERENCES users(id)

created_at TIMESTAMP NOT NULL

updated_at TIMESTAMP NOT NULL

인덱스: INDEX idx_schedules_user_id (user_id)

comments

id BIGINT AUTO_INCREMENT PRIMARY KEY

content TEXT NOT NULL

user_id BIGINT NOT NULL REFERENCES users(id)

schedule_id BIGINT NOT NULL REFERENCES schedules(id)

created_at TIMESTAMP NOT NULL

updated_at TIMESTAMP NOT NULL

인덱스: INDEX idx_comments_schedule_id (schedule_id), INDEX idx_comments_user_id (user_id)



