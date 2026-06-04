package dev.andregomes.mastersys.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class ProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProcedureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void matricularAluno(Long alunoId, Integer diaVencimento,
                                Long modalidadeId, Long planoId, Long graduacaoId) {
        jdbcTemplate.update(
                "CALL matricular_aluno(?, ?, ?, ?, ?)",
                alunoId, diaVencimento, modalidadeId, planoId, graduacaoId
        );
    }

    public void encerrarMatricula(Long matriculaId) {
        jdbcTemplate.update("CALL encerrar_matricula(?)", matriculaId);
    }

    public void gerarFaturasMensais(LocalDate mes) {
        jdbcTemplate.update("CALL gerar_faturas_mensais(?)", mes);
    }

    public void marcarFaturasVencidas() {
        jdbcTemplate.update("CALL marcar_faturas_vencidas()");
    }
}
