package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Modalidade;
import dev.andregomes.mastersys.dto.ModalidadeRequest;
import dev.andregomes.mastersys.dto.ModalidadeResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.ModalidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    public ModalidadeResponse cadastrar(ModalidadeRequest request) {
        Modalidade modalidade = request.toEntity();
        return ModalidadeResponse.fromEntity(modalidadeRepository.save(modalidade));
    }

    public List<ModalidadeResponse> listar() {
        return modalidadeRepository.findAll()
                .stream()
                .map(ModalidadeResponse::fromEntity)
                .toList();
    }

    public ModalidadeResponse buscarPorId(Long id) {
        return ModalidadeResponse.fromEntity(buscarEntidadePorId(id));
    }

    public ModalidadeResponse alterarStatus(Long id, Boolean ativa) {
        Modalidade modalidade = buscarEntidadePorId(id);
        modalidade.setAtiva(ativa);
        return ModalidadeResponse.fromEntity(modalidadeRepository.save(modalidade));
    }

    public void excluir(Long id) {
        Modalidade modalidade = buscarEntidadePorId(id);
        modalidadeRepository.delete(modalidade);
    }

    private Modalidade buscarEntidadePorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada"));
    }
}
