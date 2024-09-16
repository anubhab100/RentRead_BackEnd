# RentRead_BackEnd
Project Overview
RentRead is a Spring Boot-based RESTful API service for managing an online book rental system. It allows users to register, browse books, and rent or return books. The application includes role-based access control, where regular users can rent books, while administrators manage the book collection and view all users.

Features
User Registration: Users can register and log in.
Book Rental: Users can rent and return books.
Admin Controls: Admin users can add, update, and delete books.
Authentication & Authorization: Secure access to resources using Basic Auth.
Role-based Access Control: Two roles - USER and ADMIN.
Error Handling: Graceful error handling with meaningful HTTP status codes and error messages.
Logging: Logs for information and error tracking.
Testing: Basic unit tests using MockMvc and Mockito.


Technologies Used
Java 21
Spring Boot 3.x
Spring Security
Spring Data JPA
MySQL
Hibernate
Maven
Lombok
JUnit & Mockito
System Requirements
Java 21
Maven 3.x
MySQL 8.x
Postman (optional)


Setup Instructions
1. Clone the Repository
git clone https://github.com/your-username/RentRead.git
cd RentRead

2. MySQL Database Setup
Create a MySQL database:
CREATE DATABASE rentread_db;
Update the application.properties file with your database credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/rentread_db
spring.datasource.username=your-username
spring.datasource.password=your-password

3. Build and Run the Application
Build the application using Maven:
mvn clean install
Run the application:
java -jar target/RentRead-0.0.1-SNAPSHOT.jar

4. Accessing the Application
Once the application is running, you can access the APIs at http://localhost:8080.



Testing
The project includes basic unit tests written with JUnit and Mockito:

MockMvc is used for testing the controllers.
Mockito is used for mocking services.
Running Tests
To run the unit tests:
mvn test

Running the Application
Generate the JAR file:
mvn clean package
Run the JAR file:
java -jar target/RentRead-0.0.1-SNAPSHOT.jar
Access the APIs at:
http://localhost:8080