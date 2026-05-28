package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Aluno;
import dev.andregomes.mastersys.domain.Matricula;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaRequest(

        LocalDate dataMatricula,

        @NotNull(message = "O dia de vencimento é obrigatório.")
        @Min(value = 1, message = "O dia de vencimento deve ser entre 1 e 28.")
        @Max(value = 28, message = "O dia de vencimento deve ser entre 1 e 28.")
        Integer diaVencimento,

        @NotNull(message = "O aluno é obrigatório.")
        Long alunoId

) {
    public Matricula toEntity(Aluno aluno) {
        Matricula matricula = new Matricula();
        matricula.setDataMatricula(dataMatricula);
        matricula.setDiaVencimento(diaVencimento);
        matricula.setAluno(aluno);
        return matricula;
    }
}
