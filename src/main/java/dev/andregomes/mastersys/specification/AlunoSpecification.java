package dev.andregomes.mastersys.specification;

import dev.andregomes.mastersys.domain.Aluno;
import dev.andregomes.mastersys.dto.AlunoFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecification {

    public static Specification<Aluno> comFiltros(AlunoFiltroRequest filtro){
        return Specification.allOf(
                nomeContem(filtro.nome()),
                emailContem(filtro.email()),
                celularContem(filtro.celular()),
                cidadeContem(filtro.cidade()),
                estadoIgual(filtro.estado())
        );
    }

    private static Specification<Aluno> nomeContem(String nome){
        if (nome == null || nome.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<Aluno> emailContem(String email){
        if (email == null || email.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    private static Specification<Aluno> celularContem(String celular){
        if (celular == null || celular.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("celular")), "%" + celular.toLowerCase() + "%");
    }

    private static Specification<Aluno> cidadeContem(String cidade){
        if (cidade == null || cidade.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%");
    }

    private static Specification<Aluno> estadoIgual(String estado){
        if (estado == null || estado.isBlank()) return null;
        return (root, query, cb) -> cb.equal(cb.upper(root.get("estado")), estado.toUpperCase());
    }
}