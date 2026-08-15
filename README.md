# 🏢 RentFlow - Sistema de Gestión de Contratos de Arrendamiento

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.2-green.svg)](https://junit.org/junit5/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean-blue.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Descripción 📋

**RentFlow** es un sistema de gestión de contratos de arrendamiento construido con **Arquitectura Limpia** y **Domain-Driven Design (DDD)**. Este proyecto demuestra la separación de responsabilidades en capas, aislando la lógica de negocio de los detalles de infraestructura.

### Propósito

- Gestionar contratos de arrendamiento
- Demostrar principios de **Arquitectura Limpia**
- Implementar **DDD** con Value Objects y Agregados
- Preparar el terreno para migrar a **Spring Boot** y **PostgreSQL**


## Arquitectura

El proyecto sigue la **Arquitectura Limpia** con 3 capas principales:

````text
┌─────────────────────────────────────────────────┐
│ INFRASTRUCTURE                                  │
│ ┌─────────────────────────────────────────┐     │
│ │ APPLICATION                             │     │
│ │ ┌─────────────────────────────────┐     │     │
│ │ │ DOMAIN                          │     │     │
│ │ │ • Entidades (RentalContract)    │     │     │
│ │ │ • Value Objects (Rut)           │     │     │
│ │ │ • Repositories (interfaces)     │     │     │
│ │ └─────────────────────────────────┘     │     │
│ │ • Casos de Uso (CreateContractUseCase)  │     │
│ └─────────────────────────────────────────┘     │
│ • Adaptadores (InMemoryContractRepository)      │
│ • Configuración (BeanConfiguration)             │
└─────────────────────────────────────────────────┘
````

### Estructura de Carpetas 📁 

````text
src/main/java/com/marco/rentflow/
    ├── core/ # Capas internas (independientes)
    │ ├── domain/ # Reglas de negocio PURAS
    │ │     ├── common/
    │ │     │     └── Rut.java # Value Object auto-validante
    │ │     └── contract/
    │ │           ├── RentalContract.java # Agregado principal
    │ │           └── ContractRepository.java # Puerto (interfaz)
    │ └── application/ # Casos de Uso
    │       └── usecase/
    │             └── contract/
    │             └── CreateContractUseCase.java
    │
    └── infrastructure/ # Capas externas (detalles técnicos)
            ├── adapters/
            │      └── out/
            │           └── persistence/
            │                   └── memory/
            │                       └── InMemoryContractRepository.java # Adaptador
            └── config/
                   └── BeanConfiguration.java # Wiring de dependencias
````


