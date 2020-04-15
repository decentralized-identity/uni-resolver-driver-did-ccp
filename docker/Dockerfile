# Dockerfile for arcblock/driver-did-abt

FROM universalresolver/base-ubuntu
MAINTAINER Markus Sabadello <markus@danubetech.com>

# install dependencies

ARG GITHUB_READ_PACKAGES_OWNER
ARG GITHUB_READ_PACKAGES_TOKEN
ADD ./settings.xml /root/.m2/

# build driver-did-abt

ADD . /opt/driver-did-abt

RUN cd /opt/driver-did-abt && mvn clean install package -N -DskipTests

# done

EXPOSE 8080

RUN chmod a+rx /opt/driver-did-abt/docker/run-driver-did-abt.sh
CMD "/opt/driver-did-abt/docker/run-driver-did-abt.sh"
