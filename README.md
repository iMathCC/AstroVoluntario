# AstroVoluntário API

Plataforma de conexão entre voluntários e causas/ONGs locais.

## Tecnologias

- Java 17 | Spring Boot 3.3 | Spring Security 6 | JWT | Spring Data JPA | PostgreSQL | Flyway | BCrypt | Lombok | MapStruct | Swagger

## Funcionalidades

### Fase 01 - Autenticação
- ✅ Cadastro/Login com JWT
- ✅ Senhas criptografadas (BCrypt)
- ✅ Roles: ADMIN, VOLUNTARIO, ONG
- ✅ Swagger com autenticação JWT

### Fase 02 - Domínio Voluntário
- ✅ Perfil de voluntário (habilidades, causas, localização)
- ✅ Relacionamentos ManyToMany
- ✅ 8 migrações Flyway
- ✅ CRUD de voluntários

## Endpoints

| Método | Endpoint | Descrição | Permissão |
|--------|----------|-----------|------------|
| POST | `/auth/register` | Registrar usuário | Público |
| POST | `/auth/login` | Login → token JWT | Público |
| POST | `/voluntarios/me/perfil` | Criar perfil | VOLUNTARIO |
| GET | `/voluntarios/me` | Meu perfil | VOLUNTARIO |
| GET | `/voluntarios` | Listar todos | ADMIN/ONG |

## Como rodar

```bash
# 1. Clone
git clone https://github.com/iMathCC/user-api.git
cd user-api

# 2. Configure .env
DB_URL=jdbc:postgresql://localhost:5432/astrovoluntario
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
JWT_SECRET_KEY=sua_chave_secreta

# 3. Execute
./mvnw spring-boot:run
Acesse: http://localhost:8080/swagger-ui.html