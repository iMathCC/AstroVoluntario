CREATE TABLE IF NOT EXISTS causas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

INSERT INTO causas (nome) VALUES
    ('Meio Ambiente'),
    ('Educação'),
    ('Saúde'),
    ('Animais'),
    ('Idosos'),
    ('Crianças'),
    ('Cultura'),
    ('Esportes'),
    ('Moradia'),
    ('Alimentação')
ON CONFLICT (nome) DO NOTHING;