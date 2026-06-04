-- ============================================================
-- PROCEDURE 1: matricular_aluno
-- Cria matrícula, vincula modalidade e gera primeira fatura
-- numa única transação atômica.
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

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
$$;


-- ============================================================
-- PROCEDURE 2: encerrar_matricula
-- Encerra matrícula, fecha vínculos de modalidade e cancela
-- faturas em aberto numa única transação atômica.
-- ============================================================
CREATE OR REPLACE PROCEDURE encerrar_matricula(p_matricula_id BIGINT)
LANGUAGE plpgsql AS $$
DECLARE
    v_status VARCHAR(20);
BEGIN
    SELECT status INTO v_status
    FROM matriculas
    WHERE id = p_matricula_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Matrícula % não encontrada.', p_matricula_id;
    END IF;

    IF v_status <> 'ATIVA' THEN
        RAISE EXCEPTION 'Somente matrículas ativas podem ser encerradas.';
    END IF;

    UPDATE matriculas
    SET status           = 'ENCERRADA',
        data_encerramento = CURRENT_DATE
    WHERE id = p_matricula_id;

    UPDATE matriculas_modalidades
    SET data_fim = CURRENT_DATE
    WHERE matricula_id = p_matricula_id
      AND data_fim IS NULL;

    UPDATE faturas_matriculas
    SET status             = 'CANCELADA',
        data_cancelamento  = CURRENT_DATE
    WHERE matricula_id = p_matricula_id
      AND status        = 'ABERTA';

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
$$;


-- ============================================================
-- PROCEDURE 3: gerar_faturas_mensais  (usa CURSOR)
-- Percorre cada matrícula ativa sem fatura no mês informado
-- e insere a fatura correspondente.
-- ============================================================
CREATE OR REPLACE PROCEDURE gerar_faturas_mensais(p_mes DATE)
LANGUAGE plpgsql AS $$
DECLARE
    cur CURSOR FOR
        SELECT m.id           AS matricula_id,
               m.dia_vencimento,
               p.valor_mensal
        FROM matriculas m
        JOIN matriculas_modalidades mm ON mm.matricula_id = m.id
        JOIN planos p                  ON p.id            = mm.plano_id
        WHERE m.status     = 'ATIVA'
          AND mm.data_fim IS NULL
          AND NOT EXISTS (
              SELECT 1 FROM faturas_matriculas f
              WHERE f.matricula_id = m.id
                AND DATE_TRUNC('month', f.data_vencimento) = DATE_TRUNC('month', p_mes)
          );

    rec          RECORD;
    v_ultimo_dia INTEGER;
    v_vencimento DATE;
BEGIN
    v_ultimo_dia := EXTRACT(DAY FROM (
        DATE_TRUNC('month', p_mes) + INTERVAL '1 month - 1 day'
    ))::INT;

    OPEN cur;
    LOOP
        FETCH cur INTO rec;
        EXIT WHEN NOT FOUND;

        v_vencimento := MAKE_DATE(
            EXTRACT(YEAR  FROM p_mes)::INT,
            EXTRACT(MONTH FROM p_mes)::INT,
            LEAST(rec.dia_vencimento, v_ultimo_dia)
        );

        INSERT INTO faturas_matriculas (matricula_id, data_vencimento, valor, status)
        VALUES (rec.matricula_id, v_vencimento, rec.valor_mensal, 'ABERTA');
    END LOOP;
    CLOSE cur;

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
$$;


-- ============================================================
-- PROCEDURE 4: marcar_faturas_vencidas  (usa CURSOR)
-- Percorre faturas abertas com data de vencimento no passado
-- e marca cada uma como VENCIDA.
-- ============================================================
CREATE OR REPLACE PROCEDURE marcar_faturas_vencidas()
LANGUAGE plpgsql AS $$
DECLARE
    cur CURSOR FOR
        SELECT id
        FROM faturas_matriculas
        WHERE status       = 'ABERTA'
          AND data_vencimento < CURRENT_DATE;

    rec     RECORD;
    v_count INTEGER := 0;
BEGIN
    OPEN cur;
    LOOP
        FETCH cur INTO rec;
        EXIT WHEN NOT FOUND;

        UPDATE faturas_matriculas
        SET status = 'VENCIDA'
        WHERE id = rec.id;

        v_count := v_count + 1;
    END LOOP;
    CLOSE cur;

    RAISE NOTICE '% fatura(s) marcada(s) como vencida(s).', v_count;
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
$$;
