package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.*;
import dev.andregomes.mastersys.domain.enums.StatusMatricula;
import dev.andregomes.mastersys.dto.MatriculaModalidadeRequest;
import dev.andregomes.mastersys.dto.MatriculaModalidadeResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class MatriculaModalidadeService {

    private final MatriculaModalidadeRepository matriculaModalidadeRepository;
    private final MatriculaRepository matriculaRepository;
    private final ModalidadeRepository modalidadeRepository;
    private final PlanoRepository planoRepository;
    private final GraduacaoRepository graduacaoRepository;

    public MatriculaModalidadeService(
            MatriculaModalidadeRepository matriculaModalidadeRepository,
            MatriculaRepository matriculaRepository,
            ModalidadeRepository modalidadeRepository,
            PlanoRepository planoRepository,
            GraduacaoRepository graduacaoRepository) {
        this.matriculaModalidadeRepository = matriculaModalidadeRepository;
        this.matriculaRepository = matriculaRepository;
        this.modalidadeRepository = modalidadeRepository;
        this.planoRepository = planoRepository;
        this.graduacaoRepository = graduacaoRepository;
    }

    public MatriculaModalidadeResponse adicionar(Long matriculaId, MatriculaModalidadeRequest request) {
        Matricula matricula = buscarMatriculaPorId(matriculaId);

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraNegocioException("Não é possível adicionar modalidade a uma matrícula que não está ativa.");
        }

        Modalidade modalidade = modalidadeRepository.findById(request.modalidadeId())
                .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada"));

        Plano plano = planoRepository.findById(request.planoId())
                .orElseThrow(() -> new RegraNegocioException("Plano não encontrado"));

        Graduacao graduacao = graduacaoRepository.findById(request.graduacaoId())
                .orElseThrow(() -> new RegraNegocioException("Graduação não encontrada"));

        MatriculaModalidade mm = new MatriculaModalidade();
        mm.setMatricula(matricula);
        mm.setModalidade(modalidade);
        mm.setPlano(plano);
        mm.setGraduacao(graduacao);
        mm.setDataInicio(request.dataInicio());

        return MatriculaModalidadeResponse.fromEntity(matriculaModalidadeRepository.save(mm));
    }

    public List<MatriculaModalidadeResponse> listar(Long matriculaId) {
        buscarMatriculaPorId(matriculaId);
        return matriculaModalidadeRepository.findByMatriculaId(matriculaId)
                .stream()
                .map(MatriculaModalidadeResponse::fromEntity)
                .toList();
    }

    public void remover(Long matriculaId, Long id) {
        buscarMatriculaPorId(matriculaId);

        MatriculaModalidade mm = matriculaModalidadeRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Vínculo não encontrado"));

        if (!matriculaId.equals(mm.getMatricula().getId())) {
            throw new RegraNegocioException("Este vínculo não pertence à matrícula informada.");
        }

        if (mm.getDataFim() != null) {
            throw new RegraNegocioException("Este vínculo já foi encerrado.");
        }

        mm.setDataFim(LocalDate.now());
        matriculaModalidadeRepository.save(mm);
    }

    private Matricula buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Matrícula não encontrada"));
    }
}
