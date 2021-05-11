FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/xml-parser-spring-boot.jar xml-parser-spring-boot.jar
ENTRYPOINT ["java","-jar","xml-parser-spring-boot.jar"]