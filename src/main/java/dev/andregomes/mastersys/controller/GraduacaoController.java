package dev.andregomes.mastersys.controller;


import dev.andregomes.mastersys.repository.GraduacaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/graduacoes")
public class GraduacaoController {

    private final GraduacaoRepository graduacaoRepository;

    public GraduacaoController(GraduacaoRepository graduacaoRepository) {
        this.graduacaoRepository = graduacaoRepository;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return graduacaoRepository.findAll().stream()
                .map(g -> Map.<String, Object>of("id", g.getId(), "nome", g.getNome()))
                .toList();
    }
}
