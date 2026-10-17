<div align="center">

# 🌌 AstroVoluntário

**API REST que conecta voluntários a causas sociais e habilidades** —
autenticação JWT, perfis geolocalizados e catálogo N:N de causas

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-10-CC0200?logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?logo=swagger&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
[![CI](https://github.com/iMathCC/user-api/actions/workflows/ci.yml/badge.svg)](https://github.com/iMathCC/user-api/actions/workflows/ci.yml)

</div>

---

## O problema

Quem quer ser voluntário hoje depende de grupos de WhatsApp e formulários
espalhados — sem perfil único, sem filtro por causa ou habilidade, e sem
histórico de participação. O **AstroVoluntário** centraliza o cadastro do
voluntário (localização, disponibilidade, causas de interesse, habilidades)
e expõe uma API REST pronta para qualquer frontend consumir.

## Principais funcionalidades

- **Autenticação completa** — cadastro, login com JWT stateless e roles (`USER`/`ADMIN`).
- **Perfil de voluntário** — dados pessoais, cidade/estado, coordenadas e disponibilidade.
- **Catálogo N:N** — voluntários associados a múltiplas **causas** e **habilidades**.
- **Validação declarativa** — `@Valid`, `@NotBlank`, `@Pattern` em todos os DTOs.
- **Tratamento global de exceções** — respostas padronizadas para erros de validação e segurança.
- **Documentação interativa** — Swagger UI embutido com `@Tag`/`@Operation`.
- **Migrations versionadas** — 8 migrations Flyway (V1→V8) rastreiam todo o schema.

## Arquitetura

```text
              ┌─────────────────────┐
              │  Cliente (frontend) │
              └──────────┬──────────┘
                         │ HTTPS / JSON + Bearer JWT
              ┌──────────▼──────────┐
              │  API REST (Spring)  │
              │  Controller         │
              │   └─ Service        │
              │       └─ Repository │
              └──────────┬──────────┘
                         │ JPA + Flyway
              ┌──────────▼──────────┐
              │  PostgreSQL         │
              └─────────────────────┘
```

Modelo de domínio:

```text
User (1) ── (1) Voluntario
                    │
                    ├── (N:N) ── Habilidade
                    └── (N:N) ── Causa
```

## Stack

| Camada | Tecnologias |
|---|---|
| Backend | Java 17, Spring Boot 3.3, Spring Data JPA, Spring Security 6 (JWT) |
| Banco | PostgreSQL 16, Flyway (V1→V8) |
| Qualidade | MapStruct, Lombok, Bean Validation, GlobalExceptionHandler |
| Testes | JUnit 5, Spring Boot Test, H2 (test profile) |
| Docs | SpringDoc OpenAPI / Swagger UI |
| Infra | Docker + Docker Compose |
| CI/CD | GitHub Actions (em breve) |

## Como executar

### Opção 1: Docker Compose (recomendado)

Sobe a API + PostgreSQL sem precisar instalar nada além de Docker.

```bash
git clone https://github.com/iMathCC/user-api.git
cd user-api
cp .env.example .env
docker compose up --build
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/api-docs
- PostgreSQL acessível em `localhost:5433` (host) → `5432` (container)

### Opção 2: Maven local

Requer Java 17+ e PostgreSQL 16 rodando em `localhost:5432`.

```bash
git clone https://github.com/iMathCC/user-api.git
cd user-api
cp .env.example .env          # ajuste DB_* e JWT_SECRET_KEY
./mvnw spring-boot:run
```

## Endpoints

### Autenticação (público)

| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/register` | Registrar novo usuário |
| POST | `/auth/login` | Login → retorna JWT |

### Usuários

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| GET | `/users` | Listar todos os usuários | ✅ |
| POST | `/users` | Criar novo usuário | ✅ |
| POST | `/users/register/admin` | Registrar ADMIN (só ADMIN) | ✅ ADMIN |

### Voluntários

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | `/voluntarios/me/perfil` | Criar perfil de voluntário | ✅ |
| GET | `/voluntarios/me` | Meu perfil | ✅ |
| GET | `/voluntarios` | Listar voluntários | ✅ ADMIN/ONG |

> A lista completa e interativa está no Swagger UI (`/swagger-ui/index.html`).

## Status

Projeto em desenvolvimento ativo. Próximos passos: GitHub Actions e deploy
de referência.
