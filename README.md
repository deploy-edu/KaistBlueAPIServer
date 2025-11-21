# INSTALL

## docker

### Download

https://www.docker.com/products/docker-desktop/

### Run

```
docker compose -f docker-compose.yml up
```

## API Server

### Configuration

src/main/resources/application.properties 파일 수정

```
spring.datasource.url=jdbc:mysql://디비호스트:디피포트/디비명?&allowPublicKeyRetrieval=true
spring.datasource.username=디비유저명
spring.datasource.password=디비패스워드
```

### Run

```
./gradlew clean && ./gradlew build
java -jar ./build/libs/KaistSampleAPIServer06-0.0.1-SNAPSHOT.jar
```

## 커뮤니티 생성

서버 실행 후 관리자 계정으로 커뮤니티를 생성하려면 다음 가이드를 참고하세요:

[커뮤니티 생성 가이드](./docs/COMMUNITY_CREATION_README.md)

## API 문서

API 엔드포인트에 대한 상세한 정보는 다음 문서를 참고하세요:

[API 문서](./docs/API_DOCUMENTATION.md)
