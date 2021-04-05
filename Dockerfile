FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/counterdb-0.0.1-SNAPSHOT-LOCAL.jar counterservice.jar
ENTRYPOINT ["java","-jar","counterservice.jar"]