package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.service.FaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Faturas — Operações em Lote",
     description = "Operações globais sobre faturas executadas via stored procedures")
@RestController
@RequestMapping("/faturas")
public class FaturaOperacoesController {

    private final FaturaService faturaService;

    public FaturaOperacoesController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @Operation(
            summary = "Gerar faturas mensais (procedure + cursor)",
            description = "Gera uma fatura para cada matrícula ativa que ainda não possui fatura no mês informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Faturas geradas com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetro inválido")
            }
    )
    @PostMapping("/gerar-mensais")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void gerarFaturasMensais(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mes) {
        faturaService.gerarFaturasMensais(mes);
    }

    @Operation(
            summary = "Marcar faturas vencidas (procedure + cursor)",
            description = "Percorre todas as faturas abertas com data de vencimento no passado e marca como VENCIDA.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Faturas atualizadas com sucesso")
            }
    )
    @PostMapping("/marcar-vencidas")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarFaturasVencidas() {
        faturaService.marcarFaturasVencidas();
    }
}
