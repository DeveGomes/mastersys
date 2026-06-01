package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Assiduidade;

import java.time.LocalDateTime;

public record AssiduidadeResponse(

        Long id,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida,
        Long matriculaId

) {
    public static AssiduidadeResponse fromEntity(Assiduidade assiduidade) {
        return new AssiduidadeResponse(
                assiduidade.getId(),
                assiduidade.getDataEntrada(),
                assiduidade.getDataSaida(),
                assiduidade.getMatricula().getId()
        );
    }
}
