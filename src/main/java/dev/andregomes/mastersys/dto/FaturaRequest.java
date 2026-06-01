package dev.andregomes.mastersys.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaRequest(

        @NotNull(message = "A data de vencimento é obrigatória.")
        LocalDate dataVencimento,

        @NotNull(message = "O valor é obrigatório.")
        @Positive(message = "O valor deve ser positivo.")
        BigDecimal valor

) {}
