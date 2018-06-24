FROM tomcat:9.0-jre8
COPY /target/Infosystem-1.0.0.war /usr/local/tomcat/webapps/ 
EXPOSE 2142