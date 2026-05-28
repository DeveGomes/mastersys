package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.MatriculaModalidadeControllerDoc;
import dev.andregomes.mastersys.dto.MatriculaModalidadeRequest;
import dev.andregomes.mastersys.dto.MatriculaModalidadeResponse;
import dev.andregomes.mastersys.service.MatriculaModalidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas/{matriculaId}/modalidades")
public class MatriculaModalidadeController implements MatriculaModalidadeControllerDoc {

    private final MatriculaModalidadeService matriculaModalidadeService;

    public MatriculaModalidadeController(MatriculaModalidadeService matriculaModalidadeService) {
        this.matriculaModalidadeService = matriculaModalidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaModalidadeResponse adicionar(
            @PathVariable Long matriculaId,
            @RequestBody @Valid MatriculaModalidadeRequest request) {
        return matriculaModalidadeService.adicionar(matriculaId, request);
    }

    @GetMapping
    public List<MatriculaModalidadeResponse> listar(@PathVariable Long matriculaId) {
        return matriculaModalidadeService.listar(matriculaId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long matriculaId, @PathVariable Long id) {
        matriculaModalidadeService.remover(matriculaId, id);
    }
}
