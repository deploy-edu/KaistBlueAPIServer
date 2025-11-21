# 커뮤니티 생성 가이드

이 스크립트는 관리자 계정으로 로그인하여 10개의 대학 커뮤니티를 자동으로 생성합니다.

## 사전 요구사항

1. **서버 실행**: Spring Boot 서버가 `http://localhost:8091`에서 실행 중이어야 합니다.
2. **관리자 계정**: 관리자 계정이 생성되어 있어야 합니다.
   - 기본 관리자 계정: `admin` / `kaist1234`
   - 계정이 없다면 먼저 `/api/admin/signup` 엔드포인트를 통해 관리자 계정을 생성하세요.

## 생성되는 커뮤니티 목록

1. **KAIST 축구** - 축구 활동 및 친선 경기
2. **KAIST 밴드** - 음악 연습 및 공연
3. **KAIST 봉사** - 지역사회 봉사활동
4. **KAIST 프로그래밍** - 프로그래밍 스터디 및 프로젝트
5. **KAIST 사진** - 사진 촬영 및 전시
6. **KAIST 독서** - 독서 및 토론
7. **KAIST 댄스** - 댄스 연습 및 공연
8. **KAIST 영화** - 영화 감상 및 제작
9. **KAIST 요리** - 요리 및 베이킹
10. **KAIST 체스** - 체스 및 보드게임

## 사용 방법

### Python 스크립트 사용 (권장)

```bash
cd KaistBlueAPIServer
python3 create_communities.py
```

또는

```bash
python create_communities.py
```

### Bash 스크립트 사용

```bash
cd KaistBlueAPIServer
chmod +x create_communities.sh
./create_communities.sh
```

## 스크립트 동작 과정

1. 관리자 계정(`admin` / `kaist1234`)으로 로그인
2. JWT 토큰 획득
3. 각 커뮤니티를 순차적으로 생성
4. 생성 결과 출력

## 문제 해결

### 로그인 실패

- 관리자 계정이 존재하는지 확인하세요.
- 관리자 계정이 없다면 먼저 생성하세요:
  ```bash
  curl -X POST http://localhost:8091/api/admin/signup \
    -H "Content-Type: application/json" \
    -d '{
      "userId": "admin",
      "userName": "관리자",
      "upassword": "kaist1234",
      "email": "admin@kaist.ac.kr"
    }'
  ```

### 서버 연결 실패

- Spring Boot 서버가 실행 중인지 확인하세요.
- 포트 번호(`8091`)가 올바른지 확인하세요.

### 권한 오류

- 관리자 계정으로 로그인했는지 확인하세요.
- JWT 토큰이 유효한지 확인하세요.

## 참고사항

- 커뮤니티 생성은 관리자 권한(`ROLE_ADMIN`)이 필요합니다.
- 각 커뮤니티는 `title`과 `summary` 필드만 포함합니다.
- 이미지(`imageStr`)는 선택사항이며, 이 스크립트에서는 포함하지 않습니다.
