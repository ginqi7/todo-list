# Use the official OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled Java class into the container
COPY ./target/todo-list-0.0.1-SNAPSHOT.jar .

# Command to run the Java application
CMD ["java", "-jar", "todo-list-0.0.1-SNAPSHOT.jar"]
