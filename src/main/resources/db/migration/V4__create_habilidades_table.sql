CREATE TABLE IF NOT EXISTS habilidades (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

INSERT INTO habilidades (nome) VALUES
    ('Java'),
    ('Python'),
    ('JavaScript'),
    ('Comunicação'),
    ('Marketing Digital'),
    ('Liderança'),
    ('Primeiros Socorros'),
    ('Eventos'),
    ('Redes Sociais'),
    ('Cortar Cabelo')
ON CONFLICT (nome) DO NOTHING;
