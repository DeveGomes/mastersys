package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Matricula;
import dev.andregomes.mastersys.domain.enums.StatusMatricula;

import java.time.LocalDate;

public record MatriculaResponse(

        Long id,
        LocalDate dataMatricula,
        Integer diaVencimento,
        LocalDate dataEncerramento,
        StatusMatricula status,
        Long alunoId,
        String alunoNome

) {
    public static MatriculaResponse fromEntity(Matricula matricula) {
        return new MatriculaResponse(
                matricula.getId(),
                matricula.getDataMatricula(),
                matricula.getDiaVencimento(),
                matricula.getDataEncerramento(),
                matricula.getStatus(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome()
        );
    }
}
