package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.AlunoFiltroRequest;
import dev.andregomes.mastersys.dto.AlunoRequest;
import dev.andregomes.mastersys.dto.AlunoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Alunos",
        description = "Operações para cadastro, consulta, atualização, exclusão" +
                "e filtragem de alunos"
)
public interface AlunoControllerDoc {

    @Operation(
            summary = "Cadastrar aluno",
            description = "Cria um novo aluno no sistema de academia",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Aluno cadastrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação ou regra de négocio",
                            content = @Content(schema =  @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    AlunoResponse cadastrar(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para cadastrar aluno.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AlunoRequest.class),
                    examples = @ExampleObject(
                            name = "Aluno válido",
                            value = """                   
                       {
                      "nome": "Alan Silva",
                      "dataNascimento": "1998-06-10",
                      "sexo": "M",
                      "celular": "3493456-7890",
                      "email": "alan.silva@gmail.com",
                      "telefone": "3496543-2109",
                      "observacao": "aluno intermediário",
                      "endereco": "Rua Bernardo Guimarães",
                      "numero": "312",
                      "complemento": "",
                      "bairro": "Saraiva",
                      "cidade": "Uberlândia",
                      "estado": "MG",
                      "cep": "38408120"
                       }
               """
                    )
                    )
            )
            AlunoRequest alunoRequest
    );

    @Operation(
            summary = "Listar alunos",
            description = "Listar alunos de forma paginada, permitindo filtros opcionais por " +
                     "nome, e-mail, celular, cidade e estado",
            responses = {
                @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
    }
    )
    Page<AlunoResponse> listar(
            @Parameter(description = "Filtros opcionais para busca de alunos")
            AlunoFiltroRequest filtro,

            @Parameter(description = "Informações de paginação e ordenação")
            Pageable pageable
    );

    @Operation(
            summary = "Buscar aluno por id",
            description = "retorna os dados resumidos de um aluno específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Aluno não encotrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    AlunoResponse buscarPorId(
            @Parameter(description = "ID do aluno", example = "2", required = true)
            Long id);

    @Operation(
            summary = "Atualizar aluno",
            description = "Atualiza os dados de um aluno existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação ou regra de negócio",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    AlunoResponse atualizar(
            @Parameter(description = "ID do aluno", example = "2", required = true)
            Long id,
            @RequestBody @Valid AlunoRequest request
    );

    @Operation(
            summary = "Excluir aluno",
            description = "Remove um aluno do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Aluno não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void excluir(
            @Parameter(description = "ID do aluno", example = "2", required = true)
            Long id
    );

}
