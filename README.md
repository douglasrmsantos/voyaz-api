<div align="center">

![logo-voyaz-2](https://github.com/douglasrmsantos/voyaz-api/assets/137113815/9f8a38eb-d844-4270-a14b-09c52875e8f9)


# Voyaz API

</div>

>✈️ This is the RESTful API for Voyaz, a fictional travel agency.

## 📖 Description

This API provides the backend services for Voyaz, a fictional travel agency. See the project in Figma and Docker:

[![FIGMA](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)](https://www.figma.com/proto/zfttofYgAovmuCtBHXEQYG/Voyaz-Website?type=design&node-id=4-6408&t=aMYXP4HUZM1l7VKR-1&scaling=min-zoom&page-id=0%3A1&mode=design)
[![DOCKER](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)](https://hub.docker.com/r/douglasrmsantos/voyaz-api)

## 💻 Configuration

### Prerequisites

- [JDK 17](https://www.oracle.com/br/java/technologies/downloads/#jdk17-windows) installed
- [Maven](https://maven.apache.org/download.cgi) installed
- [OpenAi API Key](https://platform.openai.com) (Optional)

###  Database Configuration

Edit the database connection settings in the `application.properties` file.

```
spring.datasource.url=your_database/voyaz_api?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
openai.api.key=your-openai-api-key
jwt.security.token.secret=your-jwt-secret-token
```
Optionals
```
openai.model=gpt-3.5-turbo 
openai.api.url=https://api.openai.com/v1/chat/completions
```
## ▶️ Running the Project

Clone the repository and navigate to the project directory. Then, run the following command:

```
mvn spring-boot:run
```
### Getting Started

The documentation of the api will be available at http://localhost:8080/swagger-ui/index.html

![Captura de tela 2023-11-24 002701](https://github.com/douglasrmsantos/voyaz-api/assets/137113815/e6f736bc-c60a-44d7-90c4-e041de254957)

## License

MIT
