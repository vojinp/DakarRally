Application is written in Java language using Spring Boot, JPA, Hibernate, H2 database, Spring security, Lombok, Swagger and JsonwebToken.
Swagger is available on: http://localhost:8080/swagger-ui.html

Application contains 2 roles:
USER - with access to see statistic of the race, statistic of the vehicle and race leaderboard
ADMIN - with access to all endpoints

After starting application H2 in-memory database will be populated with two users:
USER (username: user, password: user)
ADMIN (username: admin, password: admin)
