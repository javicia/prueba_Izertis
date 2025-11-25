FROM eclipse-temurin:21-jre

WORKDIR /app
COPY target/Prueba_Izertis-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENV SPRING_PROFILES_ACTIVE=dev \
    SERVER_PORT=8082 \
    APP_SECURITY_USER=izertisUser \
    APP_SECURITY_PASS=izertisPass

 #app starts with the specified profile and port
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dserver.port=${SERVER_PORT} -jar app.jar"]
