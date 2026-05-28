package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Modalidade;
import dev.andregomes.mastersys.domain.Plano;
import dev.andregomes.mastersys.dto.PlanoRequest;
import dev.andregomes.mastersys.dto.PlanoResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.ModalidadeRepository;
import dev.andregomes.mastersys.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class PlanoService {

    private final PlanoRepository planoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public PlanoService(PlanoRepository planoRepository, ModalidadeRepository modalidadeRepository) {
        this.planoRepository = planoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    public PlanoResponse cadastrar(PlanoRequest request) {
        Modalidade modalidade = buscarModalidadePorId(request.modalidadeId());
        Plano plano = request.toEntity(modalidade);
        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public List<PlanoResponse> listar() {
        return planoRepository.findAll()
                .stream()
                .map(PlanoResponse::fromEntity)
                .toList();
    }

    public PlanoResponse buscarPorId(Long id) {
        return PlanoResponse.fromEntity(buscarEntidadePorId(id));
    }

    public PlanoResponse alterarStatus(Long id, Boolean ativo) {
        Plano plano = buscarEntidadePorId(id);
        plano.setAtivo(ativo);
        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public void excluir(Long id) {
        Plano plano = buscarEntidadePorId(id);
        planoRepository.delete(plano);
    }

    private Plano buscarEntidadePorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Plano não encontrado"));
    }

    private Modalidade buscarModalidadePorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Modalidade não encontrada"));
    }
}
