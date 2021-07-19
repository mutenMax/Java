FROM maven:3.8.1-jdk-8
RUN mkdir -p /usr/local/WebGenerator
COPY . /usr/local/WebGenerator

WORKDIR /usr/local/WebGenerator
RUN mvn clean install
ENTRYPOINT ["/bin/bash"]
