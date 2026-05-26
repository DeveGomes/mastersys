package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Modalidade;

public record ModalidadeResponse(

        Long id,
        String nome,
        Boolean ativa

) {

    public static ModalidadeResponse fromEntity(Modalidade modalidade) {
        return new ModalidadeResponse(
                modalidade.getId(),
                modalidade.getNome(),
                modalidade.getAtiva()
        );
    }

}
