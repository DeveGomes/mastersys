package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.PlanoControllerDoc;
import dev.andregomes.mastersys.dto.PlanoRequest;
import dev.andregomes.mastersys.dto.PlanoResponse;
import dev.andregomes.mastersys.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController implements PlanoControllerDoc {

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanoResponse cadastrar(@RequestBody @Valid PlanoRequest request) {
        return planoService.cadastrar(request);
    }

    @GetMapping
    public List<PlanoResponse> listar() {
        return planoService.listar();
    }

    @GetMapping("/{id}")
    public PlanoResponse buscarPorId(@PathVariable Long id) {
        return planoService.buscarPorId(id);
    }

    @PatchMapping("/{id}/status")
    public PlanoResponse alterarStatus(@PathVariable Long id, @RequestBody Boolean ativo) {
        return planoService.alterarStatus(id, ativo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        planoService.excluir(id);
    }
}
