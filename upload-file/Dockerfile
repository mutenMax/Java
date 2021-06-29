FROM openjdk:8
EXPOSE 8080
ADD target/upload-file.jar upload-file.jar
ENTRYPOINT ["java", "-jar", "upload-file.jar"]