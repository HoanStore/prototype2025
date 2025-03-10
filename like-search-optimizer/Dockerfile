# 1. 빌드 스테이지
FROM eclipse-temurin:17-jdk AS build

# 작업 디렉토리 설정
WORKDIR /app
# Gradle 래퍼와 설정 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
# gradlew 실행 권한 부여
RUN chmod +x ./gradlew
# 소스 코드 복사
COPY src src
# Gradle 빌드 실행 (테스트 제외)
RUN ./gradlew build -x test
# 2. 실행 스테이지
FROM eclipse-temurin:17-jre
# 작업 디렉토리 설정
WORKDIR /app
# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar
# 포트 노출 (필요한 경우 수정)
EXPOSE 8080
# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]