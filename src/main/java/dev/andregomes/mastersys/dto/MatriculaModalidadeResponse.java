package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.MatriculaModalidade;

import java.time.LocalDate;

public record MatriculaModalidadeResponse(

        Long id,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long modalidadeId,
        String modalidadeNome,
        Long planoId,
        String planoNome,
        Long graduacaoId,
        String graduacaoNome

) {
    public static MatriculaModalidadeResponse fromEntity(MatriculaModalidade mm) {
        return new MatriculaModalidadeResponse(
                mm.getId(),
                mm.getDataInicio(),
                mm.getDataFim(),
                mm.getModalidade().getId(),
                mm.getModalidade().getNome(),
                mm.getPlano().getId(),
                mm.getPlano().getNome(),
                mm.getGraduacao().getId(),
                mm.getGraduacao().getNome()
        );
    }
}
