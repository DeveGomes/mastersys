package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.doc.FaturaControllerDoc;
import dev.andregomes.mastersys.dto.FaturaRequest;
import dev.andregomes.mastersys.dto.FaturaResponse;
import dev.andregomes.mastersys.service.FaturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas/{matriculaId}/faturas")
public class FaturaController implements FaturaControllerDoc {

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FaturaResponse gerar(
            @PathVariable Long matriculaId,
            @RequestBody @Valid FaturaRequest request) {
        return faturaService.gerar(matriculaId, request);
    }

    @GetMapping
    public List<FaturaResponse> listar(@PathVariable Long matriculaId) {
        return faturaService.listar(matriculaId);
    }

    @GetMapping("/{id}")
    public FaturaResponse buscarPorId(@PathVariable Long matriculaId, @PathVariable Long id) {
        return faturaService.buscarPorId(matriculaId, id);
    }

    @PatchMapping("/{id}/pagar")
    public FaturaResponse registrarPagamento(@PathVariable Long matriculaId, @PathVariable Long id) {
        return faturaService.registrarPagamento(matriculaId, id);
    }

    @PatchMapping("/{id}/cancelar")
    public FaturaResponse cancelar(@PathVariable Long matriculaId, @PathVariable Long id) {
        return faturaService.cancelar(matriculaId, id);
    }
}
