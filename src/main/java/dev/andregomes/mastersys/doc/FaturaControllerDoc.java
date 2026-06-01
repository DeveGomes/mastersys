package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.FaturaRequest;
import dev.andregomes.mastersys.dto.FaturaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Matrícula — Faturas", description = "Geração e gestão de faturas de uma matrícula")
public interface FaturaControllerDoc {

    @Operation(
            summary = "Gerar fatura",
            description = "Gera uma nova fatura vinculada à matrícula informada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fatura gerada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    FaturaResponse gerar(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = FaturaRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "dataVencimento": "2025-06-10",
                                      "valor": 150.00
                                    }
                                    """)
                    )
            )
            FaturaRequest request
    );

    @Operation(
            summary = "Listar faturas da matrícula",
            description = "Retorna todas as faturas vinculadas à matrícula.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    List<FaturaResponse> listar(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId
    );

    @Operation(
            summary = "Buscar fatura por ID",
            description = "Retorna uma fatura específica da matrícula.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fatura encontrada"),
                    @ApiResponse(responseCode = "400", description = "Fatura não pertence à matrícula",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Fatura não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    FaturaResponse buscarPorId(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @Parameter(description = "ID da fatura", example = "1", required = true) Long id
    );

    @Operation(
            summary = "Registrar pagamento",
            description = "Marca a fatura como paga e registra a data de pagamento.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Fatura não pode ser paga no status atual",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    FaturaResponse registrarPagamento(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @Parameter(description = "ID da fatura", example = "1", required = true) Long id
    );

    @Operation(
            summary = "Cancelar fatura",
            description = "Cancela uma fatura que ainda não foi paga.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fatura cancelada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Fatura não pode ser cancelada no status atual",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    FaturaResponse cancelar(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @Parameter(description = "ID da fatura", example = "1", required = true) Long id
    );
}
