FROM maven:3.8.1-jdk-8
EXPOSE 8080
RUN mkdir -p /usr/local/upload-file
COPY . /usr/local/upload-file

WORKDIR /usr/local/upload-file
RUN mvn clean install
ENTRYPOINT ["java", "-jar", "./target/upload-file.jar"]
