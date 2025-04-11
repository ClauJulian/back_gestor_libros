# 📚 API RESTful - Gestor de Libros, Autores y Editoriales

Este proyecto es una API RESTful desarrollada en **Java con Spring Boot** que permite la gestión de libros, autores y editoriales. A través de esta API se pueden realizar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para cada una de las tres entidades. Los datos se persisten en una base de datos relacional **MySQL** utilizando **Spring Data JPA**.

## 🚀 Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Postman (para pruebas)
- Lombok
- Maven

## 💻 Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/ClauJulian/back_gestor_libros.git
   ```
2. **Configurar la base de datos:**
   - Crear una base de datos llamada `apilibreria` en MySQL.
   - Ajustar las credenciales en `application.properties`.
3. **Compilar y ejecutar el proyecto:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 📁 Estructura del Proyecto

La arquitectura del proyecto sigue una organización por capas, promoviendo la separación de responsabilidades y el mantenimiento eficiente del código:

- `controladores`: expone los endpoints REST y recibe las peticiones del cliente.
- `servicios`: contiene la lógica de negocio.
- `repositorios`: maneja la comunicación con la base de datos a través de interfaces que extienden `JpaRepository`.
- `entidades`: clases que representan las tablas en la base de datos.
- `dto` (dentro del paquete `modelos`): objetos que transportan los datos entre las capas de la aplicación.
- `excepciones`: contiene las clases de manejo de errores personalizados.
- `global exception handler`: captura y gestiona globalmente las excepciones mediante `@ControllerAdvice`.

## 🔧 Endpoints Disponibles

La API expone los siguientes endpoints REST para cada entidad:

### 📖 Libros

- `GET /libro/listar`: Obtener todos los libros
- `GET /libro/listar/{id}`: Obtener un libro por ID
- `POST /libro/crear`: Crear un nuevo libro
- `PATCH /libro/modificar/{id}`: Actualizar un libro
- `PATCH /libro/cambio_estado/{id}`: Activa o Desactiva un libro
- `DELETE /libro/baja_definitiva/{id}`: Eliminar un libro

### ✍️ Autores

- `GET /autor/listar`
- `GET /autor/listar/{id}`
- `POST /autor/crear`
- `PATCH /autor/modificar/{id}`
- `PATCH /autor/cambio_estado/{id}`
- `DELETE /autor/baja_definitiva/{id}`

### 🏢 Editoriales

- `GET /editorial/listar`
- `GET /editorial/listar/{id}`
- `POST /editorial/crear`
- `PATCH /editorial/modificar/{id}`
- `PATCH /editorial/cambio_estado/{id}`
- `DELETE /editorial/baja_definitiva/{id}`

## 🔐 Manejo de Excepciones

Se implementa una clase global de manejo de excepciones utilizando `@ControllerAdvice` y `@ExceptionHandler`, permitiendo capturar errores comunes como:

- `EntityNotFoundException`
- `IllegalArgumentException`
- `DataIntegrityViolationException`

Esto permite una mejor experiencia para el consumidor de la API al recibir mensajes claros y personalizados ante errores.

## 🛠 Dependencias Principales

Incluidas en el archivo `pom.xml`:

```xml
<dependencies>
    <!-- Spring Web para crear la API REST -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data JPA para persistencia con ORM -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Conector MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok para reducir código y mejorar legibilidad -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Boot DevTools para hot reload -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Dependencias para testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 🧪 Pruebas

Se utilizaron **Postman**  para verificar el correcto funcionamiento de los endpoints y el manejo de errores.
          |

## 💾 Configuración Base de Datos

Agregar en `application.properties` la configuración de conexión a la base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestor_libros
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## 📌 Requisitos Previos

- JDK 17+
- Maven
- MySQL
- Postman (opcional para pruebas manuales)

## 👋 Contacto
Para consultas o mejoras en el proyecto, no dudes en contactarme.