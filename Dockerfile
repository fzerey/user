# Stage 1: Download dependencies
FROM maven:3.8.5-openjdk-17 AS deps
WORKDIR /opt/app
COPY pom.xml .
COPY user-host/pom.xml user-host/
COPY user-controller/pom.xml user-controller/
COPY user-service/pom.xml user-service/
COPY user-shared/pom.xml user-shared/
COPY user-infrastructure/pom.xml user-infrastructure/
COPY user-domain/pom.xml user-domain/
COPY user-test/pom.xml user-test/
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

# Stage 2: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /opt/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/app /opt/app
COPY user-host/src user-host/src
COPY user-controller/src user-controller/src
COPY user-service/src user-service/src
COPY user-shared/src user-shared/src
COPY user-infrastructure/src user-infrastructure/src
COPY user-domain/src user-domain/src
COPY user-test/src user-test/src
RUN mvn -B -e clean package -DskipTests

# Stage 3: Run the application
FROM openjdk:17-jdk
WORKDIR /opt/app
COPY --from=build /opt/app/user-host/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
