package dev.andregomes.mastersys.doc;

import dev.andregomes.mastersys.dto.MatriculaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.ErrorResponse;

import java.util.List;

@Tag(name = "Matrículas", description = "Operações para cadastro, consulta, encerramento e cancelamento de matrículas")
public interface MatriculaControllerDoc {


    @Operation(
            summary = "Listar matrículas",
            description = "Retorna todas as matrículas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    List<MatriculaResponse> listar();

    @Operation(
            summary = "Buscar matrícula por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matrícula encontrada"),
                    @ApiResponse(responseCode = "400", description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    MatriculaResponse buscarPorId(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long id
    );

    @Operation(
            summary = "Encerrar matrícula",
            description = "Encerra uma matrícula ativa. Define a data de encerramento como a data atual.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matrícula encerrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Matrícula não encontrada ou não está ativa",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    MatriculaResponse encerrar(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long id
    );

    @Operation(
            summary = "Cancelar matrícula",
            description = "Cancela uma matrícula ativa.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matrícula cancelada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Matrícula não encontrada ou não está ativa",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    MatriculaResponse cancelar(
            @Parameter(description = "ID da matrícula", example = "1", required = true)
            Long id
    );
}
