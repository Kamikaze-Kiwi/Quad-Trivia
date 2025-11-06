# Quad Trivia
### Programming challenge

## Frontend
> [!NOTE]
> Before running any of these commands, ensure you are in the right folder `/frontend`

Prerequisites:
- Node.js (version 18.x or higher recommended)

Before running the application, make sure to run the following to install all dependencies:
```
npm install
```

The application can then be started using:
```
npm run dev
```

The application should now be available on `http://localhost:5173/`.

Depending on where the backend is running, the endpoint used to reach the backend can be changed in `.env.development`.


## Backend
> [!NOTE]
> Before running any of these commands, ensure you are in the right folder `/API`

### Manual
Prerequisites:
- Java 17 or higher
- Apache Maven 3.8+

The application can now be started using:
```
mvn spring-boot:run
```


### Docker
The backend can also be ran through Docker:
```
docker build -t trivia-api .
```
```
docker run -it --rm -p 8080:8080 trivia-api
```
Keep in mind that the endpoint used in the frontend must be changed in `.env.development`.


### Tests
The backend unit tests can be ran with:
```
mvn test
```
