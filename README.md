# WSPrestamos

 Servicio que calcula el monto de un posible prestamo con base al material y gramos
 Usa una tabla de precios almacenada en MongoDB y necesita un token generado por el API de Token

## Tecnologias

 - Java 21  
 - Spring Boot 3.5.7  
 - MongoDB  
 - Azure App Service  
 - SwaggerHub  
 - Postman  

## URL servicio

 - https://wsprestamos-f0ecf2gqecffe9dh.canadacentral-01.azurewebsites.net

## Repositorio GitHub

- Código fuente: https://github.com/andresaquinogarcia/WSPrestamos.git


## Endpoints

 - POST /api/prestamos/calcular Calcula el monto del prestamo con base al material y los gramos
 - GET  /api/prestamos/status   Verifica que el servicio este disponible

 La peticion del calculo requiere un token valido en el header
 - Authorization: Bearer <TOKEN>

## Documentacion Swagger

 La documentacion del API esta publicada en SwaggerHub en el siguiente enlace

 - [Documentacion SwaggerHub]( https://app.swaggerhub.com/apis/mexico-5bb/WSPrestamos/0.0.1)

## Coleccion Postman

 En el repositorio se incluye el archivo

 - WSPrestamos.postman_collection.json

 Contiene las peticiones

  - Calcular prestamo
  - Consultar estado del servicio

 Para probar el servicio se incluye el archivo WSPrestamos.postman_collection.json

 Pasos

 - abrir Postman  
 - importar el archivo WSPrestamos.postman_collection.json  
 - ejecutar primero la peticion Generar token del servicio WSToken para obtener un token valido  
 - copiar el valor del campo token de la respuesta  
 - seleccionar la peticion Calcular prestamo  
 - en la pestaña Headers agregar  
   Authorization: Bearer <TOKEN>  
 - enviar la peticion y revisar la respuesta del monto calculado  
 - opcionalmente ejecutar Consultar estado del servicio para verificar disponibilidad

## CI/CD con GitHub Actions

El proyecto cuenta con dos flujos automatizados en .github/workflows/

  - maven.yml - Ejecucion de integracion continua  
  - Se ejecuta al hacer push a master  
  - Configura JDK 21 y ejecuta mvn clean package
  - Compila y valida el proyecto

  - master_wsprestamos.yml - Despliegue continuo  
  - Compila el proyecto con JDK 21
  - Sube el artefacto generado y lo despliega en Azure App Service - WSPrestamos
  - Se ejecuta al hacer push en master

  Estos pipelines garantizan la compilación, validación y despliegue automático del servicio

## Despliegue

  - El sercicio se encuentra desplegado en Azure APP Service y consume el API WSToken

## Analisis de Calidad con SonarCloud

WSPrestamos se integro con SonarCLoud para realizar el analisi del codigo
Se utilizo Jacoco para las pruebas unitarias para las metricas de mantenimiento, cobertura y duplicacion

[Reporte SonarCloud](https://sonarcloud.io/project/overview?id=andresaquinogarcia_WSPrestamos)

- Evidencia de analisis en SonarCloud en docs/sonar-coverage-wsprestamos.png