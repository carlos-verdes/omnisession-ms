FROM maven

MAINTAINER nosolojava

# execute common ms until is in maven repo
RUN echo "building common-ms, home: "$HOME && cd $HOME && git clone https://github.com/nosolojava/common-ms && cd common-ms && mvn clean install

RUN echo "building omnisession-ms, home: "$HOME && cd $HOME && git clone https://github.com/nosolojava/omnisession-ms && cd omnisession-ms && mvn clean package spring-boot:repackage

EXPOSE 8080

CMD java -jar $HOME/omnisession-microservice/target/omnisession-ms.jar