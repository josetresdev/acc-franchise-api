# acc-franchise-api

API REST desarrollada con **Spring Boot** para la gestión de
franquicias, sucursales y productos.

Este proyecto hace parte de una **prueba técnica backend** y está
diseñado siguiendo principios de **arquitectura limpia**, **separación
de responsabilidades** y **buenas prácticas enterprise**, incluyendo
Docker y pipelines CI/CD con GitHub Actions.

------------------------------------------------------------------------

## Stack tecnológico

-   Java 17 (LTS)
-   Spring Boot 3.x
-   Maven 3.9.x
-   Spring Web (REST)
-   Spring Data JPA
-   Hibernate
-   MySQL 8
-   Docker & Docker Compose
-   GitHub Actions (CI / CD)
-   IntelliJ IDEA Community

------------------------------------------------------------------------

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

-   Java JDK 17
-   Maven 3.9.x
-   Git
-   Docker
-   Docker Compose

Verificar Java:

``` bash
java -version
```

Verificar Maven:

``` bash
mvn -version
```

Verificar Docker:

``` bash
docker --version
docker compose version
```

------------------------------------------------------------------------

## Instalación y ejecución local (sin Docker)

### 1. Clonar el repositorio

``` bash
git clone git@github.com:josetresdev/acc-franchise-api.git
cd acc-franchise-api
```

### 2. Ejecutar la aplicación

``` bash
mvn spring-boot:run
```

#### Configuración por variables de entorno

La aplicación está diseñada para no hardcodear valores sensibles y
soportar múltiples entornos (local, Docker, staging, producción).

##### Archivo .env

El archivo `.env` debe ubicarse en la **raíz del proyecto**, al mismo
nivel que:

    Dockerfile
    docker-compose.yml
    pom.xml
    .env

Ejemplo:

``` env
SPRING_PROFILES_ACTIVE=local

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/acc_franchise?useSSL=false&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=acc_user
SPRING_DATASOURCE_PASSWORD=acc_password

SERVER_PORT=8091
```

> El archivo `.env` no debe subirse al repositorio.

------------------------------------------------------------------------

La aplicación iniciará en:

    http://localhost:8091

------------------------------------------------------------------------

## Ejecución con Docker (recomendado)

El proyecto incluye configuración completa para ejecutar la API junto
con MySQL usando **Docker Compose**.

### 1. Construir y levantar los servicios

``` bash
docker compose up -d --build
```

Servicios levantados:

-   API Spring Boot: http://localhost:8091
-   MySQL: localhost:3306

### 2. Detener los servicios

``` bash
docker compose down
```

### 3. Ver logs

``` bash
docker compose logs -f acc-franchise-api
```

------------------------------------------------------------------------

## Configuración de Base de Datos

### MySQL (Docker)

-   Host: `localhost`
-   Puerto: `3306`
-   Base de datos: `acc_franchise`
-   Usuario: `acc_user`
-   Password: `acc_password`

Conexión JDBC:

    jdbc:mysql://localhost:3306/acc_franchise

------------------------------------------------------------------------

## Endpoint disponible

### Health Check

Permite validar que la API se encuentra activa.

**Request**

    GET /api/health

**Response**

``` json
{
  "status": "UP",
  "service": "ACC Franchise API"
}
```

------------------------------------------------------------------------

## Estructura del proyecto

    src/main/java/com/acc/franchise
    │
    ├── AccFranchiseApiApplication.java
    │
    ├── controller
    │   └── HealthController.java
    │
    ├── service
    │   └── HealthService.java
    │
    ├── domain
    │   └── Franchise.java
    │
    ├── repository
    │
    ├── dto
    │
    ├── exception
    └── config

------------------------------------------------------------------------

## Principios aplicados

-   Separación de responsabilidades por capas
-   Inyección de dependencias por constructor
-   Principios SOLID
-   Arquitectura orientada a dominio
-   DTOs como contratos de comunicación
-   Controllers sin lógica de negocio
-   Servicios desacoplados de HTTP

------------------------------------------------------------------------

## CI -- Continuous Integration

El proyecto utiliza **GitHub Actions** para validación automática.

### Pipeline CI

-   Se ejecuta en:
    -   `develop`
    -   `feature/*`
-   Acciones:
    -   Checkout del código
    -   Setup Java 17
    -   Build con Maven
    -   Ejecución de tests

Archivo:

    .github/workflows/ci.yml

------------------------------------------------------------------------

## CD -- Continuous Deployment

Pipeline de entrega continua para construcción de imágenes Docker.

### Pipeline CD

-   Se ejecuta en:
    -   `staging`
    -   `main`
-   Acciones:
    -   Build del JAR
    -   Build de imagen Docker
    -   Push a Docker Hub

Requiere secrets configurados en GitHub:

-   `DOCKERHUB_USERNAME`
-   `DOCKERHUB_TOKEN`

Archivo:

    .github/workflows/cd.yml

------------------------------------------------------------------------

## Flujo de trabajo Git

El proyecto sigue **Git Flow**:

-   `main`: rama estable
-   `staging`: pre-producción
-   `develop`: integración
-   `feature/*`: desarrollo de funcionalidades

Ejemplo:

    feature/add-franchise-crud

------------------------------------------------------------------------

## Estado actual del proyecto

-   API Spring Boot funcional
-   Dockerización completa
-   CI/CD configurado con GitHub Actions
-   Conexión MySQL operativa
-   Entidad Franchise implementada

------------------------------------------------------------------------

## Roadmap

-   CRUD completo de franquicias
-   Gestión de sucursales y productos
-   Validaciones avanzadas
-   Manejo de errores global
-   Seguridad con Spring Security
-   Versionado de API
-   Despliegue en la nube (AWS / OCI)

------------------------------------------------------------------------

## Autor

**Jose Trespalacios B.**\
Backend / Full Stack Developer\
https://josetrespalaciosbedoya.co
