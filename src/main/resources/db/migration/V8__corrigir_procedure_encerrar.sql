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
    SET status            = 'ENCERRADA',
        data_encerramento = CURRENT_DATE
    WHERE id = p_matricula_id;

    UPDATE matriculas_modalidades
    SET data_fim = CURRENT_DATE
    WHERE matricula_id = p_matricula_id
      AND data_fim IS NULL;

    UPDATE faturas_matriculas
    SET status            = 'CANCELADA',
        data_cancelamento = CURRENT_DATE
    WHERE matricula_id = p_matricula_id
      AND status        = 'ABERTA';

END;
$$;