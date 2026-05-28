package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Modalidade;
import dev.andregomes.mastersys.domain.Plano;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PlanoRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @NotNull(message = "O campo ativo é obrigatório.")
        Boolean ativo,

        @NotNull(message = "O valor mensal é obrigatório.")
        @Positive(message = "O valor mensal deve ser positivo.")
        BigDecimal valorMensal,

        @NotNull(message = "A modalidade é obrigatória.")
        Long modalidadeId

) {
    public Plano toEntity(Modalidade modalidade) {
        Plano plano = new Plano();
        preencher(plano, modalidade);
        return plano;
    }

    public void preencher(Plano plano, Modalidade modalidade) {
        plano.setNome(nome);
        plano.setAtivo(ativo);
        plano.setValorMensal(valorMensal);
        plano.setModalidade(modalidade);
    }
}
