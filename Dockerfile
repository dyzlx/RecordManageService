FROM java:8
MAINTAINER duyunzelx@outlook.com
ADD recordservice.jar /app.jar
EXPOSE 8882
ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]