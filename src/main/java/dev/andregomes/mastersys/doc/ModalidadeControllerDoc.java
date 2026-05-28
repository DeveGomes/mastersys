package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.ModalidadeRequest;
import dev.andregomes.mastersys.dto.ModalidadeResponse;
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

@Tag(name = "Modalidades", description = "Operações para cadastro, consulta, alteração de status e exclusão de modalidades")
public interface ModalidadeControllerDoc {

    @Operation(
            summary = "Cadastrar modalidade",
            description = "Cria uma nova modalidade no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Modalidade cadastrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ModalidadeResponse cadastrar(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para cadastro da modalidade",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ModalidadeRequest.class),
                            examples = @ExampleObject(
                                    name = "Modalidade válida",
                                    value = """
                                            {
                                              "nome": "Jiu-Jitsu",
                                              "ativa": true
                                            }
                                            """
                            )
                    )
            )
            ModalidadeRequest request
    );

    @Operation(
            summary = "Listar modalidades",
            description = "Retorna todas as modalidades cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    List<ModalidadeResponse> listar();

    @Operation(
            summary = "Buscar modalidade por ID",
            description = "Retorna os dados de uma modalidade específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Modalidade encontrada"),
                    @ApiResponse(responseCode = "400", description = "Modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ModalidadeResponse buscarPorId(
            @Parameter(description = "ID da modalidade", example = "1", required = true)
            Long id
    );

    @Operation(
            summary = "Alterar status da modalidade",
            description = "Ativa ou inativa uma modalidade",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ModalidadeResponse alterarStatus(
            @Parameter(description = "ID da modalidade", example = "1", required = true)
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novo status da modalidade",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    { "ativa": false }
                                    """)
                    )
            )
            Boolean ativa
    );

    @Operation(
            summary = "Excluir modalidade",
            description = "Remove uma modalidade do sistema. Falha se houver matrículas vinculadas.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Modalidade excluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    void excluir(
            @Parameter(description = "ID da modalidade", example = "1", required = true)
            Long id
    );
}
