FROM openjdk:11

VOLUME ["/tmp", "/logs"]

EXPOSE 8080

ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/demo-0.0.1-SNAPSHOT.jar"]
