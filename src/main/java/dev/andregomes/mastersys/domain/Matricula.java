package dev.andregomes.mastersys.domain;


import dev.andregomes.mastersys.domain.enums.StatusMatricula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="data_matricula")
    private LocalDate dataMatricula;

    @Column(name ="dia_vencimento")
    private Integer diaVencimento;

    @Column(name ="data_encerramento")
    private LocalDate dataEncerramento;

    @Enumerated(EnumType.STRING)
    private StatusMatricula status = StatusMatricula.ATIVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @PrePersist
    public void prePersist(){
        if(dataMatricula == null){
            dataMatricula = LocalDate.now();
        }
    }

}
