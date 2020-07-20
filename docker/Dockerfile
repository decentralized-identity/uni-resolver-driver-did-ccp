# Dockerfile for hello2mao/driver-did-ccp

FROM maven:3-jdk-11
MAINTAINER Markus Sabadello <markus@danubetech.com>

USER root

ENV PATH $JAVA_HOME/bin:$PATH

# install dependencies

RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:get -Dartifact=org.eclipse.jetty:jetty-maven-plugin:9.4.18.v20190429
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:get -DrepoUrl=https://repo.maven.apache.org/maven2 -Dartifact=org.apache.maven.plugins:maven-war-plugin:3.2.2

# build driver-did-ccp

ADD . /opt/driver-did-ccp
RUN cd /opt/driver-did-ccp && mvn clean install package -N -DskipTests

# done

EXPOSE 8080

RUN chmod a+rx /opt/driver-did-ccp/docker/run-driver-did-ccp.sh
CMD "/opt/driver-did-ccp/docker/run-driver-did-ccp.sh"
