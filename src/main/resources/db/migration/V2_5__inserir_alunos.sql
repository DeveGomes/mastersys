INSERT INTO alunos (id, nome, data_nascimento, sexo, celular, email, cidada, estado, criado_em)
VALUES
  (1, 'Carlos Souza',    '1990-03-15', 'M', '34991110001', 'carlos@email.com',   'Uberlândia', 'MG', CURRENT_TIMESTAMP),
  (2, 'Ana Lima',        '1995-07-22', 'F', '34991110002', 'ana@email.com',      'Uberlândia', 'MG', CURRENT_TIMESTAMP),
  (3, 'Pedro Alves',     '1988-11-30', 'M', '34991110003', 'pedro@email.com',    'Uberlândia', 'MG', CURRENT_TIMESTAMP),
  (4, 'Juliana Costa',   '1993-05-10', 'F', '34991110004', 'juliana@email.com',  'Uberaba',    'MG', CURRENT_TIMESTAMP),
  (5, 'Ricardo Mendes',  '1985-09-05', 'M', '34991110005', 'ricardo@email.com',  'Uberlândia', 'MG', CURRENT_TIMESTAMP);
-- coluna renomeada de cidada para cidade apenas na V5

SELECT setval('alunos_id_seq', (SELECT MAX(id) FROM alunos));

INSERT INTO graduacoes (modalidade_id, nome)
SELECT id, 'Nível Único' FROM modalidades WHERE nome IN ('Musculação', 'Funcional', 'Muay-Thay', 'Pilates');
