package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Aluno;
import dev.andregomes.mastersys.domain.Matricula;
import dev.andregomes.mastersys.domain.enums.StatusMatricula;
import dev.andregomes.mastersys.dto.MatriculaRequest;
import dev.andregomes.mastersys.dto.MatriculaResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.AlunoRepository;
import dev.andregomes.mastersys.repository.MatriculaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
    }

    public MatriculaResponse cadastrar(MatriculaRequest request) {
        Aluno aluno = buscarAlunoPorId(request.alunoId());
        Matricula matricula = request.toEntity(aluno);
        return MatriculaResponse.fromEntity(matriculaRepository.save(matricula));
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

    public MatriculaResponse encerrar(Long id) {
        Matricula matricula = buscarEntidadePorId(id);

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraNegocioException("Somente matrículas ativas podem ser encerradas.");
        }

        matricula.setStatus(StatusMatricula.ENCERRADA);
        matricula.setDataEncerramento(LocalDate.now());
        return MatriculaResponse.fromEntity(matriculaRepository.save(matricula));
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

    private Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Aluno não encontrado"));
    }
}
