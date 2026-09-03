# 🏢 RentFlow - Sistema de Gestión de Contratos de Arrendamiento

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-26.1.3-blue.svg)](https://www.docker.com/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.2-green.svg)](https://junit.org/junit5/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-success.svg)](https://swagger.io/)

---

## Descripción

**RentFlow** es un sistema de gestión de contratos de arrendamiento construido con **Arquitectura Limpia**, **Domain-Driven Design (DDD)** y **Spring Boot**. Este proyecto demuestra la separación de responsabilidades en capas, aislando la lógica de negocio de los detalles de infraestructura, con persistencia real en PostgreSQL y documentación interactiva con Swagger.

### Propósito

- Gestionar contratos de arrendamiento
- Demostrar principios de **Arquitectura Limpia** y **Hexagonal**
- Implementar **DDD** con Value Objects, Entidades y Agregados
- Exponer **API REST** con Spring Boot
- Persistencia real con **PostgreSQL** y **Docker**
- Documentación interactiva con **Swagger/OpenAPI**

---

##  Arquitectura

El proyecto sigue la **Arquitectura Hexagonal (Puertos y Adaptadores)** con 3 capas principales:

```text
┌───────────────────────────────────────────────────────────────────┐
│ INFRASTRUCTURE (Adaptadores)                                      │
│ ┌───────────────────────────────────────────────────────────────┐ │
│ │ CONTROLLER (Adaptador de Entrada)                             │ │
│ │ • Recibe peticiones HTTP                                      │ │
│ │ • Convierte DTO → Dominio                                     │ │
│ │ • Llama al Caso de Uso                                        │ │
│ └───────────────────────────────────────────────────────────────┘ │
│ │                                                                 │
│ ▼                                                                 │
│ ┌───────────────────────────────────────────────────────────────┐ │
│ │ APPLICATION (Casos de Uso)                                    │ │
│ │ • Orquesta la lógica de negocio                               │ │
│ │ • Usa Puertos (interfaces)                                    │ │
│ │ • SIN anotaciones de Spring                                   │ │
│ └───────────────────────────────────────────────────────────────┘ │
│ │                                                                 │
│ ▼                                                                 │
│ ┌───────────────────────────────────────────────────────────────┐ │
│ │ DOMAIN (Núcleo)                                               │ │
│ │ • Entidades (RentalContract)                                  │ │
│ │ • Value Objects (Rut)                                         │ │
│ │ • Puertos (ContractRepository)                                │ │
│ │ • SIN anotaciones de Spring                                   │ │
│ └───────────────────────────────────────────────────────────────┘ │
│ │                                                                 │
│ ▼                                                                 │
│ ┌───────────────────────────────────────────────────────────────┐ │
│ │ ADAPTADOR POSTGRESQL (Adaptador de Salida)                    │ │
│ │ • Implementa el Puerto ContractRepository                     │ │
│ │ • Convierte Dominio ↔ JPA Entity                              │ │
│ │ • Usa Spring Data JPA                                         │ │
│ └───────────────────────────────────────────────────────────────┘ │
└───────────────────────────────────────────────────────────────────┘
```

## Estructura de Carpetas

```text
src/main/java/com/marco/rentflow/
│
├── core/                                    # Capas internas (Java puro)
│   ├── domain/                              # Reglas de negocio PURAS
│   │   ├── common/
│   │   │   └── Rut.java                     # Value Object auto-validante
│   │   └── contract/
│   │       ├── RentalContract.java          # Agregado principal
│   │       ├── ContractStatus.java          # Enum de estado
│   │       ├── ContractRepository.java      # PUERTO (interfaz)
│   │       └── ContractNotFoundException.java # Excepción de dominio
│   └── application/                         # Casos de Uso
│       └── usecase/
│           └── contract/
│               ├── CreateContractUseCase.java
│               └── FindContractUseCase.java
│
└── infrastructure/                          # Capas externas
    ├── adapters/
    │   ├── in/                              # Adaptadores de ENTRADA
    │   │   └── web/
    │   │       └── contract/
    │   │           ├── ContractController.java    # REST Controller
    │   │           └── dto/
    │   │               ├── CreateContractRequestDTO.java
    │   │               └── ContractResponseDTO.java
    │   └── out/                             # Adaptadores de SALIDA
    │       └── persistence/
    │           ├── memory/
    │           │   └── InMemoryContractRepository.java
    │           └── postgresql/
    │               └── contract/
    │                   ├── ContractEntity.java      # JPA Entity
    │                   ├── SpringDataContractRepository.java
    │                   ├── ContractPostgresAdapter.java
    │                   └── mapper/
    │                       └── ContractPersistenceMapper.java
    └── config/
        ├── BeanConfiguration.java           # Wiring con Spring
        └── GlobalExceptionHandler.java      # Manejo global de errores
````

##  Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
| :--- | :---: | :--- |
| **Java** | `21` | Lenguaje principal |
| **Spring Boot** | `3.3.4` | Framework base |
| **Spring Web** | `3.3.4` | API REST |
| **Spring Data JPA** | `3.3.4` | Persistencia |
| **Hibernate** | `6.5.3` | ORM |
| **PostgreSQL** | `16` | Base de datos real |
| **Docker** | `26.1.3` | Virtualización de servicios |
| **Swagger/OpenAPI** | `2.5.0` | Documentación interactiva |
| **JUnit 5** | `5.10.2` | Pruebas unitarias |


---

##  Instalación y Ejecución

### Prerrequisitos
- **Java 21** instalado en tu sistema.
- **Docker Desktop** (en ejecución).
- **Maven 3.9+**.
- **Postman**, **Bruno** o Navegador Web.

### 1. Clonar el repositorio
```bash
git clone [https://github.com/marcobustosblas/rentflow-hito4.git](https://github.com/marcobustosblas/rentflow-hito4.git)
cd rentflow-hito4
```

### 2. Levantar PostgreSQL con Docker
Asegúrate de tener el puerto 5432 libre en tu máquina (sin instalaciones locales de Postgres compitiendo) antes de ejecutar:

```text
docker compose up -d
```

### 3. Ejecutar Spring Boot
Ejecuta la aplicación asegurándote de activar el perfil de desarrollo para tener acceso a Swagger:

```text
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Probar la API
A. Documentación Interactiva (Swagger)
Abre tu navegador web en la siguiente ruta:

```text
http://localhost:8080/swagger-ui/index.html
```
B. Crear un contrato (Endpoint POST)
Puedes disparar esta petición desde Bruno o Postman:

```text
HTTP
POST http://localhost:8080/api/v1/contracts
Content-Type: application/json

{
  "rut": "12345678-9",
  "rentAmount": 500000,
  "startDate": "2026-09-01"
}
```

```text
Respuesta esperada (201 Created):

JSON
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "tenantRut": "12345678-9",
  "rentAmount": 500000,
  "startDate": "2026-09-01",
  "status": "ACTIVE"
}
```

