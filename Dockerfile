FROM maven

MAINTAINER nosolojava

RUN echo "Evitando cache 456, home: "$HOME && cd $HOME && git clone https://github.com/nosolojava/omnisession-microservice && cd omnisession-microservice && mvn clean package spring-boot:repackage

EXPOSE 8080

CMD java -jar $HOME/omnisession-microservice/target/omnisession-ms.jar