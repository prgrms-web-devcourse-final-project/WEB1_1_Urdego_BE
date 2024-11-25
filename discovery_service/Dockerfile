# 1. OpenJDK 17 이미지 사용
FROM openjdk:17-jdk-slim AS build

# 2. 애플리케이션 빌드 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 파일 복사
COPY . .

# 4. Gradle 빌드를 실행 (필요한 경우)
RUN ./gradlew clean build

# 3. Spring Boot 애플리케이션 JAR 파일 복사
COPY build/libs/*SNAPSHOT.jar discovery_service.jar

# 4. 컨테이너 내부 포트 지정
EXPOSE 8761

# 5. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/eureka_server.jar"]