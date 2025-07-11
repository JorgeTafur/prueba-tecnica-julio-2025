# Prueba Tecnica Julio 2025

API REST para la actualización de cantidad en el producto de un pedido, validando reglas de negocio con manejo de errores personalizado.

## Tecnologías Usadas

- **Java 11**
- **Spring Boot**
- **MySQL**
- **JPA/Hibernate**
- **Lombok**
- **JUnit 5 + Mockito**
- **Jacoco**
- **Postman (colección adjunta para pruebas)**

## Patrones de Diseño Utilizados

- **Builder**

## 🧰 Requisitos Previos

- Java 11
- Maven
- MySQL
- IDE recomendado: IntelliJ IDEA
- Plugin de Lombok instalado y habilitado en tu IDE

## 🛠️ Configuración del Proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/JorgeTafur/prueba-tecnica-julio-2025.git
cd prueba-tecnica-julio-2025
```
2. Crea una base de datos en MySQL con el siguiente nombre:

```sql
CREATE DATABASE bd-prueba-tecnica;
```
3. Configura las credenciales de acceso a MySQL en src/main/resources/application.properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

4. Ejecuta el proyecto:
```mvn
mvn clean install

/* Ejecutar PruebaTecnicaJulio2025Application */
```

5. Marcar código generado como Generated Sources Root

```Project
/target/generated-sources/openapi/src/main/java
/* Click derecho. Mark Directory As. Generated Sources Root */
```

6. Ejemplo de ruta de consulta

```http
PUT http://localhost:8080/orders/1/products/1/quantity
```