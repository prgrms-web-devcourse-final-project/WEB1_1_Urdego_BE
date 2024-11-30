# 1. OpenJDK 17 이미지 사용
FROM openjdk:17-jdk-slim AS build

# 2. 애플리케이션 빌드 디렉토리 설정
WORKDIR /app

# 3. Spring Boot 애플리케이션 JAR 파일 복사
COPY build/libs/*.jar game_service.jar

# 4. 컨테이너 내부 포트 지정
EXPOSE 8084

# 5. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/game_service.jar"]