FROM lwieske/java-8
MAINTAINER Team Sonique

ENV LOG_DIR=/app/log
ADD morpheus-*.jar /app/morpheus.jar
WORKDIR /app

ENTRYPOINT ["java", "-d64", "-Xmx512m", "-XX:MaxMetaspaceSize=1g", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.config.location=file:./secrets/,file:./config/", "-jar", "morpheus.jar"]