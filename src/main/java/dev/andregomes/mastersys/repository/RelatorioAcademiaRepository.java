package dev.andregomes.mastersys.repository;

import dev.andregomes.mastersys.domain.FaturaMatricula;
import dev.andregomes.mastersys.projection.AlunoPorCidadeProjection;
import dev.andregomes.mastersys.projection.FaturamentoMensalProjection;
import dev.andregomes.mastersys.projection.FaturasEmAbertoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatorioAcademiaRepository extends Repository<FaturaMatricula, Long> {

    @Query(
            value = """
          SELECT
            TO_CHAR (data_vencimento, 'YYYY-MM') AS mes,
            SUM(valor) as total
           FROM faturas_matriculas
           where status = 'PAGA'
           GROUP BY TO_CHAR(data_vencimento, 'YYYY-MM')
           ORDER BY mes
           """,
         nativeQuery = true
    )
    List<FaturamentoMensalProjection> faturamentoMensal();


    @Query(
            value = """
          SELECT
            cidada,
            count (*) as quantidade
           FROM alunos
          
           GROUP BY cidade
           ORDER BY quantidade desc
           """,
            nativeQuery = true
    )
    List<AlunoPorCidadeProjection> alunoPorCidade();


    @Query(
            value = """
          SELECT
            m.id as matriculaId,
            a.nome AS alunosNome,
            f.data_vencimento as dataVencimento,
            f.valor
           FROM faturas_matriculas f
           JOIN matriculas m ON m.id = f.matricula_id
           JOIN alunos a ON a.id = m.aluno_id
           where f.status = 'ABERTA'      
           ORDER BY f.data_vencimento desc
           """,
            nativeQuery = true
    )
    List<FaturasEmAbertoProjection> faturasEmAberto();
}
