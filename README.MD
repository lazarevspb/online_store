**Online Store on Spring Java**

Welcome to our open-source online store project, built using Spring Java. This project aims to demonstrate and create a fully functional e-commerce application using modern technologies and best practices in Java development.

**Project Overview**

This project provides an online store with the following key features:

- User registration and authentication
- Product catalog with filtering and search capabilities
- Shopping cart and checkout process
- Order management for administrators
- Support for multiple payment methods
- Email notifications
- Technologies Used
- Java 17: The primary programming language
- Spring Boot: Framework for creating RESTful web applications
- Spring Data JPA: For database interactions
- Hibernate: ORM framework
- Spring Security: For security and authentication
- Thymeleaf: Template engine for server-side HTML generation
- Maven: Build system for the project
- H2: Embedded database for development
- PostgreSQL: Main database for production environment
- Installation and Setup
- Requirements
- JDK 17 or higher
- Maven 3.6.0 or higher
- PostgreSQL 13 or higher

**Installation Steps**
Clone the repository:

```
git clone https://github.com/lazarevspb/online-store.git
cd online-store
```

**Set up the database:**

Create a database in PostgreSQL and import initial data if necessary. Update the application.properties file with your database settings.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**Build and run the project:**

```
mvn clean install
mvn spring-boot:run
```

**Access the application:**

Open your browser and go to http://localhost:8080

Project Structure
src/main/java - Main source code
src/main/resources - Resources and configuration files
src/test - Test cases

**Contributing**
We welcome contributions to enhance the project. If you have ideas, bug reports, or patches, please create an issue or a pull request in our repository.

**How to Contribute**
Fork the repository
Create a new branch (git checkout -b feature/your-feature)
Make your changes and commit them (git commit -am 'Add some feature')
Push to the branch (git push origin feature/your-feature)
Create a new Pull Request

**License**
This project is licensed under the **Apache License 2.0**. 

See the LICENSE file for more details.

**Contacts**
If you have any questions or suggestions, please contact us at: v.lazarev.spb@gmail.com
