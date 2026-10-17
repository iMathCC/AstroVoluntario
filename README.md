<div align="center">

# 🌌 AstroVoluntário

**API REST que conecta voluntários a causas sociais e habilidades** —
autenticação JWT, perfis geolocalizados e catálogo N:N de causas

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-10-CC0200?logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?logo=swagger&logoColor=black)

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
- **Tratamento global de exceções** — respostas padronizadas para erros de validação e segurança.
- **Documentação interativa** — Swagger UI embutido com anotações `@Tag`/`@Operation`.
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
| Backend | Java 21, Spring Boot 3.3, Spring Data JPA, Spring Security 6 (JWT) |
| Banco | PostgreSQL 16, Flyway (V1→V8) |
| Qualidade | MapStruct, Lombok, Bean Validation, GlobalExceptionHandler |
| Testes | JUnit 5, Spring Boot Test, H2 (test profile) |
| Docs | SpringDoc OpenAPI / Swagger UI |
| CI/CD | GitHub Actions (em breve) |
| Infra | Docker + Docker Compose (em breve) |

## Como executar

Pré-requisito: Java 21+ e PostgreSQL 16 rodando.

```bash
git clone <repo>
cd FIRSTAPI
cp .env.example .env          # ajuste DB_* e JWT_SECRET_KEY
./mvnw spring-boot:run
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

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

> A lista completa e interativa está no Swagger UI.

## Status

Projeto em desenvolvimento ativo. Próximos passos: Docker Compose,
GitHub Actions e deploy de referência.