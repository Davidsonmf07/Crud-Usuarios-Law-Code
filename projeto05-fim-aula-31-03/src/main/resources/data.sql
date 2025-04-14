-- ADM
INSERT INTO usuario (id_usuario, nome, email, senha, is_ativo, fk_adm) VALUES
(1, 'Operador 1', 'operador1@example.com', 'opedador123', true, 1),

-- FUNCIONÁRIO (NÃO ADM)
(2, 'Operador 2', 'operador2@example.com', 'operador456', true, NULL);
