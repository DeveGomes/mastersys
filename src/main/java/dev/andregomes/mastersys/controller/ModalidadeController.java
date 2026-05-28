package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.ModalidadeControllerDoc;
import dev.andregomes.mastersys.dto.ModalidadeRequest;
import dev.andregomes.mastersys.dto.ModalidadeResponse;
import dev.andregomes.mastersys.service.ModalidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeController implements ModalidadeControllerDoc {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModalidadeResponse cadastrar(@RequestBody @Valid ModalidadeRequest request) {
        return modalidadeService.cadastrar(request);
    }

    @GetMapping
    public List<ModalidadeResponse> listar() {
        return modalidadeService.listar();
    }

    @GetMapping("/{id}")
    public ModalidadeResponse buscarPorId(@PathVariable Long id) {
        return modalidadeService.buscarPorId(id);
    }

    @PatchMapping("/{id}/status")
    public ModalidadeResponse alterarStatus(@PathVariable Long id, @RequestBody Boolean ativa) {
        return modalidadeService.alterarStatus(id, ativa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        modalidadeService.excluir(id);
    }
}
