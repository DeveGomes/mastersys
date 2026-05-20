package dev.andregomes.mastersys.exception;

import dev.andregomes.mastersys.domain.Aluno;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
         LocalDateTime timestamp,
         Integer status,
         String erro,
         List<String> mensagens
)
{

}
