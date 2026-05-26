package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Modalidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModalidadeRequest(

        @NotBlank
        @Size(max=150)
        String nome,

        @NotNull
        Boolean ativa
) {
        public Modalidade toEntity () {
        Modalidade modalidade = new Modalidade();
        preencherModalidade(modalidade);
        return modalidade;
    }

        public void preencherModalidade (Modalidade modalidade){
            modalidade.setNome(nome);
            modalidade.setAtiva(ativa);
    }

 }
