package dev.andregomes.mastersys.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaModalidadeRequest(

        @NotNull(message = "A modalidade é obrigatória.")
        Long modalidadeId,

        @NotNull(message = "O plano é obrigatório.")
        Long planoId,

        @NotNull(message = "A graduação é obrigatória.")
        Long graduacaoId,

        LocalDate dataInicio

) {}
