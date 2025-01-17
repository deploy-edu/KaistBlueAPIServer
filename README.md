# INSTALL 

## docker 

### Download

https://www.docker.com/products/docker-desktop/

### Run

```
docker-compose -f docker-compose.yml up
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
