CREATE TABLE IF NOT EXISTS voluntarios (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    data_nascimento DATE,
    telefone VARCHAR(20),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    disponibilidade VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_voluntarios_cidade_estado ON voluntarios(cidade, estado);
CREATE INDEX IF NOT EXISTS idx_voluntarios_user_id ON voluntarios(user_id);