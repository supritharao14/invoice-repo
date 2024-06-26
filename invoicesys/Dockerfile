# Use a base image with Java 17 installed
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at path /app
COPY target/invoicesys-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]