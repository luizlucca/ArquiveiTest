FROM openjdk:8-jdk-alpine
LABEL maintainer="luizusp@gmail.com"
VOLUME /tmp
EXPOSE 8085
COPY ./target/worker-0.0.1-SNAPSHOT.jar arquivei-nfe-worker.jar
ENTRYPOINT ["java","-jar","/arquivei-nfe-worker.jar"]


