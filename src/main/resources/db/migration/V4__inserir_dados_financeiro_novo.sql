INSERT INTO faturas_matriculas(matricula_id, data_vencimento, valor, status)
     SELECT
       m.id,
       CURRENT_DATE - INTERVAL '30 days',
       180.00,
       'ABERTA'
       FROM matriculas m
       WHERE m.aluno_id = 5;