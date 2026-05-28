package dev.andregomes.mastersys.repository;

import dev.andregomes.mastersys.domain.MatriculaModalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaModalidadeRepository extends JpaRepository<MatriculaModalidade, Long> {

    List<MatriculaModalidade> findByMatriculaId(Long matriculaId);
}
