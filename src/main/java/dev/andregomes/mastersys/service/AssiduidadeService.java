package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Assiduidade;
import dev.andregomes.mastersys.domain.Matricula;
import dev.andregomes.mastersys.domain.enums.StatusMatricula;
import dev.andregomes.mastersys.dto.AssiduidadeRequest;
import dev.andregomes.mastersys.dto.AssiduidadeResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.AssiduidadeRepository;
import dev.andregomes.mastersys.repository.MatriculaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class AssiduidadeService {

    private final AssiduidadeRepository assiduidadeRepository;
    private final MatriculaRepository matriculaRepository;

    public AssiduidadeService(AssiduidadeRepository assiduidadeRepository, MatriculaRepository matriculaRepository) {
        this.assiduidadeRepository = assiduidadeRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public AssiduidadeResponse registrarEntrada(Long matriculaId, AssiduidadeRequest request) {
        Matricula matricula = buscarMatriculaPorId(matriculaId);

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraNegocioException("Somente alunos com matrícula ativa podem registrar presença.");
        }

        Assiduidade assiduidade = new Assiduidade();
        assiduidade.setMatricula(matricula);
        if (request != null && request.dataEntrada() != null) {
            assiduidade.setDataEntrada(request.dataEntrada());
        }

        return AssiduidadeResponse.fromEntity(assiduidadeRepository.save(assiduidade));
    }

    public AssiduidadeResponse registrarSaida(Long matriculaId, Long id) {
        buscarMatriculaPorId(matriculaId);
        Assiduidade assiduidade = buscarAssiduidadeVinculada(matriculaId, id);

        if (assiduidade.getDataSaida() != null) {
            throw new RegraNegocioException("Saída já registrada para este registro de presença.");
        }

        assiduidade.setDataSaida(LocalDateTime.now());
        return AssiduidadeResponse.fromEntity(assiduidadeRepository.save(assiduidade));
    }

    public List<AssiduidadeResponse> listar(Long matriculaId) {
        buscarMatriculaPorId(matriculaId);
        return assiduidadeRepository.findByMatriculaId(matriculaId)
                .stream()
                .map(AssiduidadeResponse::fromEntity)
                .toList();
    }

    private Assiduidade buscarAssiduidadeVinculada(Long matriculaId, Long id) {
        Assiduidade assiduidade = assiduidadeRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Registro de presença não encontrado."));

        if (!matriculaId.equals(assiduidade.getMatricula().getId())) {
            throw new RegraNegocioException("Este registro não pertence à matrícula informada.");
        }

        return assiduidade;
    }

    private Matricula buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Matrícula não encontrada."));
    }
}
