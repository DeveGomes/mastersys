package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.PlanoRequest;
import dev.andregomes.mastersys.dto.PlanoResponse;
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

@Tag(name = "Planos", description = "Operações para cadastro, consulta, alteração de status e exclusão de planos")
public interface PlanoControllerDoc {

    @Operation(
            summary = "Cadastrar plano",
            description = "Cria um novo plano vinculado a uma modalidade",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plano cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação ou modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    PlanoResponse cadastrar(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para cadastro do plano",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PlanoRequest.class),
                            examples = @ExampleObject(
                                    name = "Plano válido",
                                    value = """
                                            {
                                              "nome": "Mensal Jiu-Jitsu",
                                              "ativo": true,
                                              "valorMensal": 120.00,
                                              "modalidadeId": 1
                                            }
                                            """
                            )
                    )
            )
            PlanoRequest request
    );

    @Operation(
            summary = "Listar planos",
            description = "Retorna todos os planos cadastrados com o nome da modalidade vinculada",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    List<PlanoResponse> listar();

    @Operation(
            summary = "Buscar plano por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plano encontrado"),
                    @ApiResponse(responseCode = "400", description = "Plano não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    PlanoResponse buscarPorId(
            @Parameter(description = "ID do plano", example = "1", required = true)
            Long id
    );

    @Operation(
            summary = "Alterar status do plano",
            description = "Ativa ou inativa um plano",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Plano não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    PlanoResponse alterarStatus(
            @Parameter(description = "ID do plano", example = "1", required = true)
            Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novo status do plano",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = "false"))
            )
            Boolean ativo
    );

    @Operation(
            summary = "Excluir plano",
            description = "Remove um plano do sistema. Falha se houver matrículas vinculadas.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Plano excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Plano não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    void excluir(
            @Parameter(description = "ID do plano", example = "1", required = true)
            Long id
    );
}
