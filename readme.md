# acc-franchise-api

API REST desarrollada con Spring Boot para la gestión de franquicias, sucursales y productos.

Este proyecto hace parte de una prueba técnica backend y está diseñado siguiendo principios de arquitectura limpia, separación de responsabilidades y buenas prácticas enterprise.

---

## Stack tecnológico

- Java 17 (LTS)
- Spring Boot 3.5.9
- Maven 3.9.12
- Spring Web (REST)
- IntelliJ IDEA Community

---

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java JDK 17
- Maven 3.9.12
- Git
- IntelliJ IDEA (recomendado)

Verificar Java:

```bash
java -version
```

Verificar Maven:

```bash
mvn -version
```

---

## Instalación y ejecución local

### 1. Clonar el repositorio

```bash
git clone git@github.com:josetresdev/acc-franchise-api.git
cd acc-franchise-api
```

### 2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación iniciará en:

```
http://localhost:8091
```

---

## Endpoint disponible

### Health Check

Permite validar que la API se encuentra activa.

**Request**
```
GET /api/health
```

**Response**
```json
{
  "status": "UP",
  "service": "ACC Franchise API"
}
```

---

## Estructura del proyecto

```
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
├── dto
│   └── HealthResponseDto.java
│
├── exception
└── config
```

---

## Principios aplicados

- Separación de responsabilidades por capas
- Inyección de dependencias por constructor
- Principios SOLID
- DTOs como contratos de comunicación
- Controllers sin lógica de negocio
- Servicios independientes de HTTP

---

## Flujo de trabajo Git

El proyecto sigue Git Flow:

- main: rama estable
- develop: rama de integración
- feature/*: desarrollo de funcionalidades

Ejemplo de rama feature:

```
feature/start-spring-boot
```

---

## Estado actual del proyecto

- Proyecto Spring Boot inicializado
- Arquitectura base definida
- Endpoint de health check funcional

---

## Roadmap

- Integración con JPA
- Modelo de dominio Franchise, Branch y Product
- Persistencia con base de datos
- Endpoints CRUD
- Dockerización
- Despliegue en la nube

---

## Autor

Jose Trespalacios B.
josetrespalaciosbedoya.co 
Backend / Full Stack Developer
