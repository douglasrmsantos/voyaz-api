FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar voyaz.jar
ARG JAVA_OPTS_VOYAZ
ENV JAVA_OPTS=$JAVA_OPTS_VOYAZ
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar voyaz.jar"]
