CREATE TABLE IF NOT EXISTS voluntario_causas (
    voluntario_id BIGINT NOT NULL REFERENCES voluntarios(id) ON DELETE CASCADE,
    causa_id BIGINT NOT NULL REFERENCES causas(id) ON DELETE CASCADE,
    PRIMARY KEY (voluntario_id, causa_id)
    );