package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.FaturaMatricula;
import dev.andregomes.mastersys.domain.Matricula;
import dev.andregomes.mastersys.domain.enums.StatusFatura;
import dev.andregomes.mastersys.dto.FaturaRequest;
import dev.andregomes.mastersys.dto.FaturaResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.FaturaMatriculaRepository;
import dev.andregomes.mastersys.repository.MatriculaRepository;
import dev.andregomes.mastersys.repository.ProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class FaturaService {

    private final FaturaMatriculaRepository faturaRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProcedureRepository procedureRepository;

    public FaturaService(FaturaMatriculaRepository faturaRepository,
                         MatriculaRepository matriculaRepository,
                         ProcedureRepository procedureRepository) {
        this.faturaRepository = faturaRepository;
        this.matriculaRepository = matriculaRepository;
        this.procedureRepository = procedureRepository;
    }

    public FaturaResponse gerar(Long matriculaId, FaturaRequest request) {
        Matricula matricula = buscarMatriculaPorId(matriculaId);

        FaturaMatricula fatura = new FaturaMatricula();
        fatura.setMatricula(matricula);
        fatura.setDataVencimento(request.dataVencimento());
        fatura.setValor(request.valor());

        return FaturaResponse.fromEntity(faturaRepository.save(fatura));
    }

    public List<FaturaResponse> listar(Long matriculaId) {
        buscarMatriculaPorId(matriculaId);
        return faturaRepository.findByMatriculaId(matriculaId)
                .stream()
                .map(FaturaResponse::fromEntity)
                .toList();
    }

    public FaturaResponse buscarPorId(Long matriculaId, Long id) {
        buscarMatriculaPorId(matriculaId);
        return FaturaResponse.fromEntity(buscarFaturaVinculada(matriculaId, id));
    }

    public FaturaResponse registrarPagamento(Long matriculaId, Long id) {
        buscarMatriculaPorId(matriculaId);
        FaturaMatricula fatura = buscarFaturaVinculada(matriculaId, id);

        if (fatura.getStatus() != StatusFatura.ABERTA && fatura.getStatus() != StatusFatura.VENCIDA) {
            throw new RegraNegocioException("Somente faturas abertas ou vencidas podem ser pagas.");
        }

        fatura.setStatus(StatusFatura.PAGA);
        fatura.setDataPagamento(LocalDateTime.now());
        return FaturaResponse.fromEntity(faturaRepository.save(fatura));
    }

    public FaturaResponse cancelar(Long matriculaId, Long id) {
        buscarMatriculaPorId(matriculaId);
        FaturaMatricula fatura = buscarFaturaVinculada(matriculaId, id);

        if (fatura.getStatus() == StatusFatura.PAGA) {
            throw new RegraNegocioException("Faturas pagas não podem ser canceladas.");
        }

        if (fatura.getStatus() == StatusFatura.CANCELADA) {
            throw new RegraNegocioException("Esta fatura já está cancelada.");
        }

        fatura.setStatus(StatusFatura.CANCELADA);
        fatura.setDataCancelamento(java.time.LocalDate.now());
        return FaturaResponse.fromEntity(faturaRepository.save(fatura));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void gerarFaturasMensais(LocalDate mes) {
        procedureRepository.gerarFaturasMensais(mes);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void marcarFaturasVencidas() {
        procedureRepository.marcarFaturasVencidas();
    }

    private FaturaMatricula buscarFaturaVinculada(Long matriculaId, Long id) {
        FaturaMatricula fatura = faturaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Fatura não encontrada."));

        if (!matriculaId.equals(fatura.getMatricula().getId())) {
            throw new RegraNegocioException("Esta fatura não pertence à matrícula informada.");
        }

        return fatura;
    }

    private Matricula buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Matrícula não encontrada."));
    }
}
