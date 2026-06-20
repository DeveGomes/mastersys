-- ============================================================
-- 1. Corrigir procedure matricular_aluno (remover COMMIT/ROLLBACK)
-- ============================================================
CREATE OR REPLACE PROCEDURE matricular_aluno(
    p_aluno_id       BIGINT,
    p_dia_vencimento INTEGER,
    p_modalidade_id  BIGINT,
    p_plano_id       BIGINT,
    p_graduacao_id   BIGINT
)
LANGUAGE plpgsql AS $$
DECLARE
    v_matricula_id  BIGINT;
    v_valor_mensal  NUMERIC(10,2);
    v_ultimo_dia    INTEGER;
    v_vencimento    DATE;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM alunos WHERE id = p_aluno_id) THEN
        RAISE EXCEPTION 'Aluno % não encontrado.', p_aluno_id;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM modalidades WHERE id = p_modalidade_id AND ativa = TRUE) THEN
        RAISE EXCEPTION 'Modalidade % não encontrada ou inativa.', p_modalidade_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM planos
        WHERE id = p_plano_id AND modalidade_id = p_modalidade_id AND ativo = TRUE
    ) THEN
        RAISE EXCEPTION 'Plano % não encontrado, inativo ou não pertence à modalidade.', p_plano_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM graduacoes
        WHERE id = p_graduacao_id AND modalidade_id = p_modalidade_id
    ) THEN
        RAISE EXCEPTION 'Graduação % não encontrada ou não pertence à modalidade.', p_graduacao_id;
    END IF;

    INSERT INTO matriculas (aluno_id, data_matricula, dia_vencimento, status)
    VALUES (p_aluno_id, CURRENT_DATE, p_dia_vencimento, 'ATIVA')
    RETURNING id INTO v_matricula_id;

    INSERT INTO matriculas_modalidades (matricula_id, modalidade_id, graduacao_id, plano_id, data_inicio)
    VALUES (v_matricula_id, p_modalidade_id, p_graduacao_id, p_plano_id, CURRENT_DATE);

    SELECT valor_mensal INTO v_valor_mensal FROM planos WHERE id = p_plano_id;

    v_ultimo_dia := EXTRACT(DAY FROM (
        DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month - 1 day'
    ))::INT;

    v_vencimento := MAKE_DATE(
        EXTRACT(YEAR  FROM CURRENT_DATE)::INT,
        EXTRACT(MONTH FROM CURRENT_DATE)::INT,
        LEAST(p_dia_vencimento, v_ultimo_dia)
    );

    IF v_vencimento < CURRENT_DATE THEN
        v_vencimento := v_vencimento + INTERVAL '1 month';
    END IF;

    INSERT INTO faturas_matriculas (matricula_id, data_vencimento, valor, status)
    VALUES (v_matricula_id, v_vencimento, v_valor_mensal, 'ABERTA');

END;
$$;


-- ============================================================
-- 2. Planos para modalidades sem plano
-- ============================================================
INSERT INTO planos (nome, valor_mensal, ativo, modalidade_id)
SELECT 'Mensal', 160, TRUE, id FROM modalidades WHERE nome = 'Jiu-Jitsu'
  AND NOT EXISTS (SELECT 1 FROM planos WHERE modalidade_id = (SELECT id FROM modalidades WHERE nome = 'Jiu-Jitsu'));

INSERT INTO planos (nome, valor_mensal, ativo, modalidade_id)
SELECT 'Mensal', 130, TRUE, id FROM modalidades WHERE nome = 'Muay-Thay'
  AND NOT EXISTS (SELECT 1 FROM planos WHERE modalidade_id = (SELECT id FROM modalidades WHERE nome = 'Muay-Thay'));

INSERT INTO planos (nome, valor_mensal, ativo, modalidade_id)
SELECT 'Mensal', 140, TRUE, id FROM modalidades WHERE nome = 'Pilates'
  AND NOT EXISTS (SELECT 1 FROM planos WHERE modalidade_id = (SELECT id FROM modalidades WHERE nome = 'Pilates'));


-- ============================================================
-- 3. Graduacoes para modalidades sem graduacao
-- ============================================================
INSERT INTO graduacoes (nome, modalidade_id)
SELECT 'Nível Único', id FROM modalidades WHERE nome = 'Muay-Thay'
  AND NOT EXISTS (SELECT 1 FROM graduacoes WHERE modalidade_id = (SELECT id FROM modalidades WHERE nome = 'Muay-Thay'));

INSERT INTO graduacoes (nome, modalidade_id)
SELECT 'Nível Único', id FROM modalidades WHERE nome = 'Pilates'
  AND NOT EXISTS (SELECT 1 FROM graduacoes WHERE modalidade_id = (SELECT id FROM modalidades WHERE nome = 'Pilates'));