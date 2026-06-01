package dev.andregomes.mastersys.repository;

import dev.andregomes.mastersys.domain.FaturaMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaturaMatriculaRepository extends JpaRepository<FaturaMatricula, Long> {

    List<FaturaMatricula> findByMatriculaId(Long matriculaId);
}
