package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.AssiduidadeRequest;
import dev.andregomes.mastersys.dto.AssiduidadeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Matrícula — Assiduidade", description = "Controle de entrada e saída dos alunos")
public interface AssiduidadeControllerDoc {

    @Operation(
            summary = "Registrar entrada",
            description = "Registra a entrada do aluno. Requer matrícula ativa. A data de entrada padrão é o momento atual.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entrada registrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Matrícula não está ativa",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    AssiduidadeResponse registrarEntrada(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = false,
                    content = @Content(
                            schema = @Schema(implementation = AssiduidadeRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "dataEntrada": "2025-05-28T08:00:00"
                                    }
                                    """)
                    )
            )
            AssiduidadeRequest request
    );

    @Operation(
            summary = "Registrar saída",
            description = "Registra a saída do aluno para um registro de presença em aberto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Saída registrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Saída já registrada ou registro não pertence à matrícula",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    AssiduidadeResponse registrarSaida(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId,
            @Parameter(description = "ID do registro de presença", example = "1", required = true) Long id
    );

    @Operation(
            summary = "Listar registros de presença",
            description = "Retorna todos os registros de entrada e saída da matrícula.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    List<AssiduidadeResponse> listar(
            @Parameter(description = "ID da matrícula", example = "1", required = true) Long matriculaId
    );
}
