package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.FaturaMatricula;
import dev.andregomes.mastersys.domain.enums.StatusFatura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FaturaResponse(

        Long id,
        LocalDate dataVencimento,
        BigDecimal valor,
        StatusFatura status,
        LocalDateTime dataPagamento,
        LocalDate dataCancelamento,
        Long matriculaId

) {
    public static FaturaResponse fromEntity(FaturaMatricula fatura) {
        return new FaturaResponse(
                fatura.getId(),
                fatura.getDataVencimento(),
                fatura.getValor(),
                fatura.getStatus(),
                fatura.getDataPagamento(),
                fatura.getDataCancelamento(),
                fatura.getMatricula().getId()
        );
    }
}
