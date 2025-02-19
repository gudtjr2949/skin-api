# base 이미지 설정
FROM openjdk:17-jdk-slim

# jar 파일 위치를 변수로 설정
ARG JAR_FILE=build/libs/skin-api-0.0.1-SNAPSHOT.jar

# jar 파일을 컨테이너 내부에 복사
COPY ${JAR_FILE} skin-api.jar

# 실행 명령어
CMD ["java", "-jar", "skin-api.jar"]