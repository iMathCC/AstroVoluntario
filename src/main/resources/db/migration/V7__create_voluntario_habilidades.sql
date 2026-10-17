CREATE TABLE IF NOT EXISTS voluntario_habilidades (
    voluntario_id BIGINT NOT NULL REFERENCES voluntarios(id) ON DELETE CASCADE,
    habilidade_id BIGINT NOT NULL REFERENCES habilidades(id) ON DELETE CASCADE,
    PRIMARY KEY (voluntario_id, habilidade_id)
    );