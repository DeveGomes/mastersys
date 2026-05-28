package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.MatriculaControllerDoc;
import dev.andregomes.mastersys.dto.MatriculaRequest;
import dev.andregomes.mastersys.dto.MatriculaResponse;
import dev.andregomes.mastersys.service.MatriculaService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse cadastrar(@RequestBody @Valid MatriculaRequest request) {
        return matriculaService.cadastrar(request);
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
