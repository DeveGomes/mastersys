package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Matricula;
import dev.andregomes.mastersys.domain.enums.StatusMatricula;
import dev.andregomes.mastersys.dto.MatriculaCompletaRequest;
import dev.andregomes.mastersys.dto.MatriculaResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.MatriculaRepository;
import dev.andregomes.mastersys.repository.ProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ProcedureRepository procedureRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            ProcedureRepository procedureRepository) {
        this.matriculaRepository = matriculaRepository;
        this.procedureRepository = procedureRepository;
    }


    public List<MatriculaResponse> listar() {
        return matriculaRepository.findAll()
                .stream()
                .map(MatriculaResponse::fromEntity)
                .toList();
    }

    public MatriculaResponse buscarPorId(Long id) {
        return MatriculaResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void matricular(MatriculaCompletaRequest request) {
        procedureRepository.matricularAluno(
                request.alunoId(),
                request.diaVencimento(),
                request.modalidadeId(),
                request.planoId(),
                request.graduacaoId()
        );
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public MatriculaResponse encerrar(Long id) {
        procedureRepository.encerrarMatricula(id);
        return MatriculaResponse.fromEntity(
                matriculaRepository.findById(id)
                        .orElseThrow(() -> new RegraNegocioException("Matrícula não encontrada"))
        );
    }

    public MatriculaResponse cancelar(Long id) {
        Matricula matricula = buscarEntidadePorId(id);

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraNegocioException("Somente matrículas ativas podem ser canceladas.");
        }

        matricula.setStatus(StatusMatricula.CANCELADA);
        return MatriculaResponse.fromEntity(matriculaRepository.save(matricula));
    }

    private Matricula buscarEntidadePorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Matrícula não encontrada"));
    }

}
