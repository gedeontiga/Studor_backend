FROM openjdk:19

EXPOSE 8080

# Set environment variables for MySQL
ENV MYSQL_DATABASE=app_db
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=

# Set environment variables for Spring Boot
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql_studor:3306/app_db
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=

COPY target/orientation_student.jar orientation_student.jar
ENTRYPOINT [ "java", "-jar", "/orientation_student.jar"]