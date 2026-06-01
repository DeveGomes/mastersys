package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.AssiduidadeControllerDoc;
import dev.andregomes.mastersys.dto.AssiduidadeRequest;
import dev.andregomes.mastersys.dto.AssiduidadeResponse;
import dev.andregomes.mastersys.service.AssiduidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas/{matriculaId}/assiduidade")
public class AssiduidadeController implements AssiduidadeControllerDoc {

    private final AssiduidadeService assiduidadeService;

    public AssiduidadeController(AssiduidadeService assiduidadeService) {
        this.assiduidadeService = assiduidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssiduidadeResponse registrarEntrada(
            @PathVariable Long matriculaId,
            @RequestBody(required = false) AssiduidadeRequest request) {
        return assiduidadeService.registrarEntrada(matriculaId, request);
    }

    @PatchMapping("/{id}/saida")
    public AssiduidadeResponse registrarSaida(
            @PathVariable Long matriculaId,
            @PathVariable Long id) {
        return assiduidadeService.registrarSaida(matriculaId, id);
    }

    @GetMapping
    public List<AssiduidadeResponse> listar(@PathVariable Long matriculaId) {
        return assiduidadeService.listar(matriculaId);
    }
}
