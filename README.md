# spring-demo
Spring előadáshoz készült demo app.

Előfeltétel: java és rabbitmq telepítése.

Hat microservice, elsőnek eureka indítandó.

Windows: gradlew.bat bootRun
Unix: ./gradlew bootRun

Eureka a 8761-es porton érhető el, többi service localhost:PORT/swagger-ui.html-en.

Portok:
book - 8080
inventory - 8081
order - 8082
review - 8083
bff - 8084

