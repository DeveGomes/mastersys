package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.MatriculaControllerDoc;
import dev.andregomes.mastersys.dto.MatriculaCompletaRequest;
import dev.andregomes.mastersys.dto.MatriculaResponse;
import dev.andregomes.mastersys.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController implements MatriculaControllerDoc {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }


    @Operation(
            summary = "Matricular aluno (procedure)",
            description = "Cria matrícula, vincula modalidade e gera primeira fatura numa única transação via stored procedure.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aluno matriculado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio")
            }
    )
    @PostMapping("/matricular")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void matricular(@RequestBody @Valid MatriculaCompletaRequest request) {
        matriculaService.matricular(request);
    }

    @GetMapping
    public List<MatriculaResponse> listar() {
        return matriculaService.listar();
    }

    @GetMapping("/{id}")
    public MatriculaResponse buscarPorId(@PathVariable Long id) {
        return matriculaService.buscarPorId(id);
    }

    @PatchMapping("/{id}/encerrar")
    public MatriculaResponse encerrar(@PathVariable Long id) {
        return matriculaService.encerrar(id);
    }

    @PatchMapping("/{id}/cancelar")
    public MatriculaResponse cancelar(@PathVariable Long id) {
        return matriculaService.cancelar(id);
    }
}
