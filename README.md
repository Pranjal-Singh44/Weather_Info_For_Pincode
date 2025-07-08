# Weather Info for Pincode (Spring Boot Project)

This is a Spring Boot REST API that fetches and caches real-time weather information for any Indian pincode on a given date. It uses the OpenWeather API for geocoding and weather data and persists results in an in-memory H2 database to optimize future requests.

---

## Features

- Converts Pincode to Latitude/Longitude using OpenWeather Geocoding API
- Retrieves current weather using OpenWeather OneCall API
- Caches pincode and weather data in a relational database (H2)
- Optimized to avoid redundant API calls for previously requested data
- Fully unit-tested using JUnit and Mockito
- RESTful API with JSON request/response format
- Easily testable using Postman or Swagger

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Web, JPA, H2 Database
- OpenWeather API (Geocoding + Weather)
- JUnit 5 + Mockito (Unit Testing)
- Maven (Build Tool)

---

## API Endpoint

### `POST /api/weather`

#### Request Body

```json
{
  "pincode": "411014",
  "forDate": "2024-07-07"
}
```

#### Sample Response

```json
{
  "pincode": "411014",
  "latitude": 18.5685,
  "longitude": 73.9158,
  "city": "Pune",
  "date": "2024-07-07",
  "temperature": 32.5,
  "humidity": 60,
  "description": "clear sky"
}
```

---

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/Pranjal-Singh44/Weather_Info_For_Pincode.git
cd <your_repo_folder>
```

### 2. Configure OpenWeather API Key

Update `src/main/resources/application.properties`:

```properties
weather.api.key=your_api_key_here
```

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The app will start on: `http://localhost:8080`

---

## H2 Database Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: _(leave blank)_

---

## Running Tests

```bash
mvn test
```

Includes:

- WeatherService unit test (with mocked DB/API)
- WeatherController test (mocked service & full REST layer)

---

## Project Structure

```
com.FreightFox.weather_for_pin
├── controller
├── service
├── dto
├── model
├── repository
├── exception
└── WeatherApiApplication.java
```

---

## 🤝 Acknowledgments

- [OpenWeather API](https://openweathermap.org/api)
- Spring Boot, JPA, H2, JUnit, Mockito
