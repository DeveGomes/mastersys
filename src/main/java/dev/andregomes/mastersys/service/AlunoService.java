package dev.andregomes.mastersys.service;

import dev.andregomes.mastersys.domain.Aluno;
import dev.andregomes.mastersys.dto.AlunoFiltroRequest;
import dev.andregomes.mastersys.dto.AlunoRequest;
import dev.andregomes.mastersys.dto.AlunoResponse;
import dev.andregomes.mastersys.exception.RegraNegocioException;
import dev.andregomes.mastersys.repository.AlunoRepository;
import dev.andregomes.mastersys.specification.AlunoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    public final AlunoRepository alunoRepository;


    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse cadastrar(AlunoRequest request){

        if(request.email() != null && alunoRepository.existsByEmail(request.email())){
            throw new RegraNegocioException("Já existe um aluno cadastrado com esse email");
        }

        Aluno aluno = request.toEntity();
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(aluno);
    }

    public Page<AlunoResponse> listar (AlunoFiltroRequest filtro, Pageable pageable){
        return alunoRepository.findAll(AlunoSpecification.comFiltros(filtro),
                pageable).map(AlunoResponse::fromEntity);
    }

    public AlunoResponse buscarPorId(Long id){

        Aluno aluno = buscarEntidadePorId(id);
        return AlunoResponse.fromEntity(aluno);
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request){
        Aluno aluno = buscarEntidadePorId(id);
        request.preencher(aluno);
        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(alunoAtualizado);
    }

    public void excluir (Long id){
        Aluno aluno = buscarEntidadePorId(id);
        alunoRepository.delete(aluno);
    }

    private Aluno buscarEntidadePorId(Long id){
        return alunoRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Aluno não encontrado"));

    }

}
