# KAIST Spring Boot Backend API 문서

## 개요
이 문서는 KAIST Spring Boot Backend API의 모든 엔드포인트에 대한 상세 정보를 제공합니다.

**Base URL**: `http://localhost:8091`

**인증 방식**: JWT Bearer Token (대부분의 엔드포인트에서 `Authorization: Bearer {token}` 헤더 필요)

---

## 목차
1. [인증 (Authentication)](#인증-authentication)
2. [사용자 관리 (User Management)](#사용자-관리-user-management)
3. [커뮤니티 (Community)](#커뮤니티-community)
4. [게시판 (Board)](#게시판-board)
5. [댓글 (Comment)](#댓글-comment)
6. [데모 API (Demo APIs)](#데모-api-demo-apis)

---

## 인증 (Authentication)

### 1. 로그인
사용자 인증을 수행하고 JWT 토큰을 반환합니다.

**Endpoint**: `POST /auth/authenticate`

**인증 필요**: 없음

**Request Body**:
```json
{
    "userId": "kaist",
    "uPassword": "kaist1234"
}
```

**Response**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "status": 200
}
```

**Response Headers**:
- `Authorization: Bearer {token}`

---

## 사용자 관리 (User Management)

### 1. 사용자 회원가입
일반 사용자 계정을 생성합니다.

**Endpoint**: `POST /api/signup`

**인증 필요**: 없음

**Request Body**:
```json
{
    "userId": "kaist",
    "userName": "김카이",
    "uPassword": "kaist1234",
    "email": "kaist@gmail.com"
}
```

**Response**: 생성된 User 객체

---

### 2. 관리자 회원가입
관리자 계정을 생성합니다.

**Endpoint**: `POST /api/admin/signup`

**인증 필요**: 없음

**Request Body**:
```json
{
    "userId": "admin",
    "userName": "관리자",
    "uPassword": "kaist1234",
    "email": "admin@kaist.ac.kr"
}
```

**Response**: 생성된 User 객체 (ROLE_ADMIN 권한)

---

### 3. 사용자 정보 조회 (ROLE_USER)
일반 사용자 권한으로 사용자 정보를 조회합니다.

**Endpoint**: `GET /api/users/{userId}`

**인증 필요**: ROLE_USER

**Path Parameters**:
- `userId` (Long): 사용자 ID

**Request Headers**:
```
Authorization: Bearer {token}
```

**Response**: Optional<User> 객체

---

### 4. 사용자 정보 조회 (ROLE_USER, ROLE_ADMIN)
일반 사용자 또는 관리자 권한으로 사용자 정보를 조회합니다.

**Endpoint**: `GET /api/users/all/{userId}`

**인증 필요**: ROLE_USER 또는 ROLE_ADMIN

**Path Parameters**:
- `userId` (Long): 사용자 ID

**Request Headers**:
```
Authorization: Bearer {token}
```

**Response**: Optional<User> 객체

---

### 5. 사용자 정보 조회 (ROLE_ADMIN)
관리자 권한으로 사용자 정보를 조회합니다.

**Endpoint**: `GET /api/users/admin/{userId}`

**인증 필요**: ROLE_ADMIN

**Path Parameters**:
- `userId` (Long): 사용자 ID

**Request Headers**:
```
Authorization: Bearer {token}
```

**Response**: Optional<User> 객체

---

### 6. 사용자 정보 조회 (DTO, ROLE_USER, ROLE_ADMIN)
이메일을 기반으로 사용자 정보를 DTO 형식으로 조회합니다.

**Endpoint**: `GET /api/users/all/dto/{userId}`

**인증 필요**: ROLE_USER 또는 ROLE_ADMIN

**Path Parameters**:
- `userId` (String): 사용자 이메일

**Request Headers**:
```
Authorization: Bearer {token}
```

**Response**: UserDTO 객체

**Example**:
```
GET /api/users/all/dto/kaist@kaist.ac.kr
```

---

## 커뮤니티 (Community)

### 1. 커뮤니티 생성
새로운 커뮤니티를 생성합니다. (관리자 전용)

**Endpoint**: `POST /community/create`

**인증 필요**: ROLE_ADMIN

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "title": "커뮤니티 제목",
    "summary": "커뮤니티 설명",
    "type": "image/png"
}
```

**Response**:
```json
{
    "status": 200,
    "message": "정상적으로 처리되었습니다.",
    "data": {
        "id": 1,
        "title": "커뮤니티 제목",
        "summary": "커뮤니티 설명",
        "status": "C",
        "createdAt": "2024-01-01T00:00:00",
        "type": "image/png"
    }
}
```

---

### 2. 커뮤니티 조회
모든 커뮤니티 목록을 조회합니다.

**Endpoint**: `GET /community/list`

**인증 필요**: 없음

**Response**:
```json
{
    "status": 200,
    "message": "정상적으로 처리되었습니다.",
    "data": [
        {
            "id": 1,
            "title": "커뮤니티 제목",
            "summary": "커뮤니티 설명",
            "status": "C",
            "createdAt": "2024-01-01T00:00:00"
        }
    ]
}
```

---

### 3. 커뮤니티 가입
사용자를 커뮤니티에 가입시킵니다.

**Endpoint**: `POST /community/user/add`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "communityId": 1,
    "userId": 1,
    "nickName": "닉네임",
    "sortNo": 1
}
```

**Response**: Message 객체

---

### 4. 커뮤니티 회원 정보 수정
커뮤니티 회원의 정보를 수정합니다.

**Endpoint**: `POST /community/user/edit`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "id": 1,
    "communityId": 1,
    "userId": 1,
    "nickName": "수정된 닉네임",
    "sortNo": 1
}
```

**Response**: Message 객체

---

### 5. 커뮤니티 탈퇴
사용자를 커뮤니티에서 탈퇴시킵니다.

**Endpoint**: `POST /community/user/delete`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "id": 1,
    "communityId": 1,
    "userId": 1
}
```

**Response**:
```json
{
    "status": 200,
    "message": "정상적으로 처리되었습니다.",
    "data": []
}
```

---

### 6. 커뮤니티 가입목록
사용자가 가입한 커뮤니티 목록을 조회합니다.

**Endpoint**: `POST /community/list/user`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**: 없음 (Authentication 객체에서 사용자 정보 추출)

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": [
        {
            "id": 1,
            "communityId": 1,
            "userId": 1,
            "nickName": "닉네임",
            "sortNo": 1,
            "status": "C",
            "createdAt": "2024-01-01T00:00:00"
        }
    ]
}
```

---

### 7. 커뮤니티 이미지 저장
커뮤니티 이미지를 저장합니다.

**Endpoint**: `POST /community/image/save`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "id": 1,
    "imageStr": "base64_encoded_image_string",
    "type": "image/png"
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": {
        "id": 1,
        "title": "커뮤니티 제목",
        "summary": "커뮤니티 설명",
        "image": "...",
        "type": "image/png"
    }
}
```

---

## 게시판 (Board)

### 1. 게시글 작성
커뮤니티에 게시글을 작성합니다.

**Endpoint**: `POST /board/save`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "communityId": 1,
    "title": "게시글 제목",
    "content": "게시글 내용"
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": {
        "id": 1,
        "communityId": 1,
        "userId": 1,
        "title": "게시글 제목",
        "content": "게시글 내용",
        "status": "C",
        "createdAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00"
    }
}
```

---

### 2. 게시글 조회
커뮤니티의 게시글 목록을 조회합니다.

**Endpoint**: `POST /board/list`

**인증 필요**: 없음

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
```

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": [
        {
            "id": 1,
            "communityId": 1,
            "userId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용",
            "status": "C",
            "createdAt": "2024-01-01T00:00:00",
            "updatedAt": "2024-01-01T00:00:00"
        }
    ]
}
```

---

### 3. 게시글 삭제
게시글을 삭제합니다.

**Endpoint**: `POST /board/delete`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": []
}
```

---

## 댓글 (Comment)

### 1. 댓글 작성
게시글에 댓글을 작성합니다.

**Endpoint**: `POST /board/comment/save`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "communityId": 1,
    "boardId": 1,
    "content": "댓글 내용"
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": [
        {
            "id": 1,
            "communityId": 1,
            "boardId": 1,
            "userId": 1,
            "content": "댓글 내용",
            "status": "C",
            "createdAt": "2024-01-01T00:00:00",
            "updatedAt": "2024-01-01T00:00:00"
        }
    ]
}
```

---

### 2. 댓글 조회
게시글의 댓글 목록을 조회합니다.

**Endpoint**: `POST /board/comment/list`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "boardId": 1
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": [
        {
            "id": 1,
            "communityId": 1,
            "boardId": 1,
            "userId": 1,
            "content": "댓글 내용",
            "status": "C",
            "createdAt": "2024-01-01T00:00:00",
            "updatedAt": "2024-01-01T00:00:00"
        }
    ]
}
```

---

### 3. 댓글 삭제
댓글을 삭제합니다.

**Endpoint**: `POST /board/comment/delete`

**인증 필요**: 필요 (Authentication 객체 사용)

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
Authorization: Bearer {token}
```

**Request Body**:
```json
{
    "id": 1
}
```

**Response**:
```json
{
    "status": 200,
    "message": "ok",
    "data": []
}
```

---

## 데모 API (Demo APIs)

이 섹션의 API들은 데모/테스트 목적으로 제공됩니다.

### 1. Hello
간단한 문자열을 반환합니다.

**Endpoint**: `GET /kaist/hello`

**인증 필요**: 없음

**Response**: `"hello spring boot!"`

---

### 2. 사용자 목록 조회
모든 사용자 목록을 조회합니다.

**Endpoint**: `GET /kaist/users`

**인증 필요**: 없음

**Response**: User 객체 배열

---

### 3. 사용자 이름으로 조회
사용자 이름으로 사용자를 검색합니다.

**Endpoint**: `GET /kaist/user/{name}`

**인증 필요**: 없음

**Path Parameters**:
- `name` (String): 사용자 이름

**Example**:
```
GET /kaist/user/김카이
```

**Response**: User 객체 배열

---

### 4. 아이템 조회
간단한 HashMap 객체를 반환합니다.

**Endpoint**: `GET /kaist/item`

**인증 필요**: 없음

**Response**:
```json
{
    "kaist": "hello wold"
}
```

---

### 5. 아이템 목록 조회
아이템 목록을 반환합니다.

**Endpoint**: `GET /kaist/items`

**인증 필요**: 없음

**Response**: HashMap 객체 배열

---

### 6. 아이템 추가
아이템을 추가합니다.

**Endpoint**: `POST /kaist/item/add`

**인증 필요**: 없음

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
```

**Request Body**:
```json
{
    "kaist": "hello world"
}
```

**Response**: 요청한 HashMap 객체

---

### 7. 사용자 검색
조건에 맞는 사용자를 검색합니다.

**Endpoint**: `POST /kaist/user/search`

**인증 필요**: 없음

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
```

**Request Body**:
```json
{
    "userName": "김카이"
}
```

**Response**: HashMap 객체 배열

---

### 8. 사용자 페이지 조회
페이징된 사용자 목록을 조회합니다.

**Endpoint**: `POST /kaist/user/page`

**인증 필요**: 없음

**Request Headers**:
```
Content-Type: application/json;charset=UTF-8
```

**Request Body**:
```json
{
    "email": "kaist@kaist.ac.kr",
    "page": 0,
    "size": 10
}
```

**Response**: Page<User> 객체

---

## 공통 응답 형식

대부분의 API는 다음 형식의 응답을 반환합니다:

```json
{
    "status": 200,
    "message": "ok" | "error" | "정상적으로 처리되었습니다.",
    "data": {} | [] | null
}
```

### Status Codes
- `200 OK`: 요청이 성공적으로 처리됨
- `400 BAD REQUEST`: 잘못된 요청
- `401 UNAUTHORIZED`: 인증 필요
- `403 FORBIDDEN`: 권한 없음
- `404 NOT FOUND`: 리소스를 찾을 수 없음
- `500 INTERNAL SERVER ERROR`: 서버 오류

---

## 인증 및 권한

### JWT 토큰 사용
대부분의 엔드포인트는 JWT 토큰 인증이 필요합니다. 로그인 API를 통해 토큰을 받은 후, 다음 헤더를 포함해야 합니다:

```
Authorization: Bearer {token}
```

### 권한 레벨
- **ROLE_USER**: 일반 사용자 권한
- **ROLE_ADMIN**: 관리자 권한

### 권한이 필요한 엔드포인트
- `POST /community/create`: ROLE_ADMIN
- `GET /api/users/{userId}`: ROLE_USER
- `GET /api/users/all/{userId}`: ROLE_USER 또는 ROLE_ADMIN
- `GET /api/users/admin/{userId}`: ROLE_ADMIN
- `GET /api/users/all/dto/{userId}`: ROLE_USER 또는 ROLE_ADMIN

---

## 엔티티 구조

### User
```json
{
    "id": 1,
    "userId": "kaist",
    "userName": "김카이",
    "uPassword": "kaist1234",
    "status": "ACTIVE",
    "email": "kaist@kaist.ac.kr",
    "token": "...",
    "crateDate": "2024-01-01T00:00:00",
    "role": "ROLE_USER"
}
```

### Community
```json
{
    "id": 1,
    "title": "커뮤니티 제목",
    "summary": "커뮤니티 설명",
    "status": "C",
    "createdAt": "2024-01-01T00:00:00",
    "image": "...",
    "imageStr": "base64_string",
    "type": "image/png"
}
```

### Board
```json
{
    "id": 1,
    "communityId": 1,
    "userId": 1,
    "title": "게시글 제목",
    "content": "게시글 내용",
    "status": "C",
    "createdAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00"
}
```

### BoardComment
```json
{
    "id": 1,
    "communityId": 1,
    "boardId": 1,
    "userId": 1,
    "content": "댓글 내용",
    "status": "C",
    "createdAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00"
}
```

### CommunityUser
```json
{
    "id": 1,
    "communityId": 1,
    "userId": 1,
    "sortNo": 1,
    "nickName": "닉네임",
    "status": "C",
    "createdAt": "2024-01-01T00:00:00",
    "image": "...",
    "imageStr": "base64_string",
    "type": "image/png"
}
```

---

## 주의사항

1. **포트 번호**: 기본 포트는 `8091`입니다. `application.properties`에서 변경 가능합니다.
2. **인증 토큰**: 로그인 후 받은 토큰은 30분(1800초) 동안 유효합니다.
3. **이미지 업로드**: 커뮤니티 이미지는 Base64 인코딩된 문자열로 전송해야 합니다.
4. **상태 코드**: `status` 필드는 일반적으로 `"C"` (활성) 또는 `"D"` (삭제됨) 값을 가집니다.

---

## 변경 이력

- **2024-01-XX**: 초기 문서 작성
  - 모든 엔드포인트 추가
  - 경로 수정 사항 반영
  - 누락된 엔드포인트 추가

