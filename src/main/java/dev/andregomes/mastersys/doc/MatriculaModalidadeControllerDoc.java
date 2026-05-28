package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.MatriculaModalidadeRequest;
import dev.andregomes.mastersys.dto.MatriculaModalidadeResponse;
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

@Tag(name = "Matrícula — Modalidades", description = "Vincula e gerencia modalidades dentro de uma matrícula")
public interface MatriculaModalidadeControllerDoc {

    @Operation(
            summary = "Adicionar modalidade à matrícula",
            description = "Vincula uma modalidade, plano e graduação a uma matrícula ativa.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    MatriculaModalidadeResponse adicionar(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long matriculaId,
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MatriculaModalidadeRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "modalidadeId": 1,
                                      "planoId": 1,
                                      "graduacaoId": 1,
                                      "dataInicio": "2025-05-01"
                                    }
                                    """)
                    )
            )
            MatriculaModalidadeRequest request
    );

    @Operation(
            summary = "Listar modalidades da matrícula",
            description = "Retorna todas as modalidades vinculadas a uma matrícula, incluindo as já encerradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    List<MatriculaModalidadeResponse> listar(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long matriculaId
    );

    @Operation(
            summary = "Remover modalidade da matrícula",
            description = "Encerra o vínculo setando a data de fim. O histórico é preservado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vínculo encerrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Vínculo não encontrado ou já encerrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    void remover(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long matriculaId,
            @Parameter(description = "ID do vínculo", example = "1", required = true)
            Long id
    );
}
