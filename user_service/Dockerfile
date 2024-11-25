# 1. OpenJDK 17 이미지 사용
FROM openjdk:17-jdk-slim AS build

# 2. 애플리케이션 빌드 디렉토리 설정
WORKDIR /app

# 3. Gradle Wrapper 복사
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x ./gradlew

# 4. 프로젝트 파일 복사
COPY . .

# 5. Gradle 빌드를 실행 (필요한 경우)
RUN ./gradlew clean build

# 6. Spring Boot 애플리케이션 JAR 파일 복사
COPY build/libs/0-SNAPSHOT.jar user_service.jar

# 7. 컨테이너 내부 포트 지정
EXPOSE 8081

# 8. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/user_service.jar"]