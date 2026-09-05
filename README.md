# 🏢 RentFlow API (rentflow-api-springboot)

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-26.1.3-blue.svg)](https://www.docker.com/)

API RESTful para la gestión de contratos de arrendamiento. Desarrollada con Java 21 y Spring Boot 3.3.4, siguiendo los principios de **Arquitectura Hexagonal** y **Domain-Driven Design (DDD)**.

---

## Arquitectura

| Capa | Propósito |
|------|-----------|
| **Domain** | Entidades, Value Objects y excepciones de negocio. **100% Java puro** (sin anotaciones de Spring). |
| **Application** | Casos de uso (Create, Find, Cancel, Renew). Orquestan el flujo de datos. |
| **Infrastructure** | Adaptadores: JPA, controladores REST, configuración CORS y manejo global de excepciones. |

---

## Requisitos Técnicos de Infraestructura

### Docker Desktop (Obligatorio) 🐳

**Este proyecto utiliza una base de datos virtualizada con Docker. No utiliza PostgreSQL instalado localmente en tu sistema operativo.**

| Aspecto | Detalle Técnico |
|---------|-----------------|
| **Base de datos** | PostgreSQL 16.4 (Imagen oficial `postgres:16-alpine`) |
| **Puerto mapeado** | `5432:5432` (Host:Contenedor) |
| **Persistencia** | Volumen Docker `postgres_data` para mantener los datos |
| **Usuario** | `admin` |
| **Base de datos** | `rentflow` |

**¿Por qué es obligatorio Docker Desktop?**

1. La base de datos **solo existe dentro del contenedor**; no hay una instalación local de PostgreSQL.
2. Garantiza que la aplicación funcione en cualquier entorno sin conflictos de versiones.
3. El archivo `compose.yml` define todas las variables de entorno (`POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB`).

---

## Instalación y Ejecución (Guía para Evaluadores)

### Paso 0: Verificar Docker Desktop
**Asegúrate de tener Docker Desktop instalado y en ejecución.**

- **Windows/Mac:** Abre Docker Desktop y verifica que el ícono esté verde (motor en ejecución).
- **Linux:** Ejecuta `sudo systemctl status docker` para verificar que el servicio esté activo.

### Paso 1: Levantar la Base de Datos Virtual (PostgreSQL)
```bash
# Navega a la raíz del proyecto
cd rentflow-api-springboot

# Levanta el contenedor en segundo plano
docker compose up -d

# Verifica que el contenedor esté corriendo
docker ps
```
**Salida esperada:**
```
CONTAINER ID   IMAGE                 COMMAND                  CREATED         STATUS         PORTS                    NAMES
abc123...      postgres:16-alpine    "docker-entrypoint.s…"   5 seconds ago   Up 5 seconds   0.0.0.0:5432->5432/tcp   rentflow-db
```

### Paso 2: Ejecutar el Backend (Spring Boot)
```bash
# Limpia y compila el proyecto
mvn clean compile

# Ejecuta el servidor en el perfil de desarrollo
mvn spring-boot:run "-Dspring.profiles.active=dev"
```
**Verifica que el servidor arranque correctamente:**
```
Started Application in X.XXX seconds
Tomcat started on port 8080 (http) with context path ''
```

**Swagger UI:** http://localhost:8080/swagger-ui.html

### Paso 3: Probar la Conexión a la Base de Datos Virtual (Opcional)
```bash
# Ingresa al contenedor y conecta a la base de datos
docker exec -it rentflow-db psql -U admin -d rentflow

# Dentro de psql, ejecuta un comando de prueba
rentflow=# SELECT 1;
```
**Salida esperada:**
```
?column?
----------
        1
(1 row)
```

---

## Endpoints REST

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/contracts` | Listar todos |
| GET | `/api/v1/contracts/{id}` | Buscar por ID |
| POST | `/api/v1/contracts` | Crear contrato |
| PATCH | `/api/v1/contracts/{id}/cancel` | Cancelar |
| PATCH | `/api/v1/contracts/{id}/renew` | Renovar |

---

## Guía de Ejecución Full-Stack (Para Evaluadores)

**Paso 1: Base de Datos (Virtualizada con Docker)**
```bash
docker compose up -d
```

**Paso 2: Lógica de Negocio (Spring Boot)**
```bash
mvn clean spring-boot:run "-Dspring.profiles.active=dev"
```

**Paso 3: Interfaz de Usuario (Frontend React)**
```bash
cd ../rentflow-frontend
npm install
npm run dev
```

**Paso 4: Validación End-to-End**
Abrir `http://localhost:5173`. Crear un contrato y verificar que se guarda exitosamente (status 201 Created).