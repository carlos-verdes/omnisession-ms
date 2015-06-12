FROM maven

MAINTAINER nosolojava

RUN echo "Evitando cache 123, home: "$HOME && cd $HOME && git clone https://github.com/nosolojava/omnisession-microservice && cd omnisession-microservice && mvn clean package spring-boot:repackage


CMD java -jar $HOME/omnisession-microservice/target/omnisession-microservice.jar