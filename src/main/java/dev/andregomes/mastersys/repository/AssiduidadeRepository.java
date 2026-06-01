package dev.andregomes.mastersys.repository;

import dev.andregomes.mastersys.domain.Assiduidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssiduidadeRepository extends JpaRepository<Assiduidade, Long> {

    List<Assiduidade> findByMatriculaId(Long matriculaId);
}
