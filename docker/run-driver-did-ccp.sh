#!/bin/sh

cd /opt/driver-did-ccp/
mvn --settings settings.xml jetty:run -P war
