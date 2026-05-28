package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Plano;

import java.math.BigDecimal;

public record PlanoResponse(

        Long id,
        String nome,
        Boolean ativo,
        BigDecimal valorMensal,
        Long modalidadeId,
        String modalidadeNome

) {
    public static PlanoResponse fromEntity(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getAtivo(),
                plano.getValorMensal(),
                plano.getModalidade().getId(),
                plano.getModalidade().getNome()
        );
    }
}
