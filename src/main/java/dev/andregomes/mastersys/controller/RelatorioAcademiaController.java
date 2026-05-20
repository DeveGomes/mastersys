package dev.andregomes.mastersys.controller;

import dev.andregomes.mastersys.projection.AlunoPorCidadeProjection;
import dev.andregomes.mastersys.projection.FaturamentoMensalProjection;
import dev.andregomes.mastersys.projection.FaturasEmAbertoProjection;
import dev.andregomes.mastersys.repository.RelatorioAcademiaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioAcademiaController {

    private final RelatorioAcademiaRepository relatorioAcademiaRepository;


    public RelatorioAcademiaController(RelatorioAcademiaRepository relatorioAcademiaRepository) {
        this.relatorioAcademiaRepository = relatorioAcademiaRepository;
    }

    @GetMapping("/faturamento-mensal")
    public List<FaturamentoMensalProjection> faturamentoMensal(){
        return relatorioAcademiaRepository.faturamentoMensal();
    }

    @GetMapping("/aluno-por-cidade")
    public List<AlunoPorCidadeProjection> alunoPorCidade(){
        return relatorioAcademiaRepository.alunoPorCidade();
    }

    @GetMapping("/faturas-em-aberto")
    public List<FaturasEmAbertoProjection> faturasEmAberto(){
        return relatorioAcademiaRepository.faturasEmAberto();
    }
}
