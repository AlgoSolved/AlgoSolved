FROM openjdk:17

WORKDIR /usr/app/backend

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/backend-0.0.1-SNAPSHOT.jar ${JAR_PATH}/backend-0.0.1-SNAPSHOT.jar

EXPOSE "8080"

CMD ["java", "-jar", "./build/libs//backend-0.0.1-SNAPSHOT.jar"]