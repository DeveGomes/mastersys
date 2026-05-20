package dev.andregomes.mastersys.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FaturasEmAbertoProjection {

    Long getMatriculaId();
    String getAlunosNome();
    LocalDate getDataVencimento();
    BigDecimal getValor();

}
