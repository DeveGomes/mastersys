package dev.andregomes.mastersys.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatriculaCompletaRequest(

        @NotNull(message = "O aluno é obrigatório.")
        Long alunoId,

        @NotNull(message = "O dia de vencimento é obrigatório.")
        @Min(value = 1,  message = "O dia de vencimento deve ser entre 1 e 31.")
        @Max(value = 31, message = "O dia de vencimento deve ser entre 1 e 31.")
        Integer diaVencimento,

        @NotNull(message = "A modalidade é obrigatória.")
        Long modalidadeId,

        @NotNull(message = "O plano é obrigatório.")
        Long planoId,

        @NotNull(message = "A graduação é obrigatória.")
        Long graduacaoId

) {}
