package dev.andregomes.mastersys.dto;

import dev.andregomes.mastersys.domain.Aluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlunoRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve ter o máximo 150 caracteres")
        String nome,
        @Past(message = "A data de nascimento deve estar no passado.")
        LocalDate dataNascimento,

        @Size(max = 1, message = "O sexo deve ter no máximo 1 caractere.")
        String sexo,

        @Size(max = 30, message = "O telefone deve ter no máximo 30 caracteres.")
        String telefone,

        @NotBlank(message = "O E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "O e-mail deve ter o máximo 150 caracteres.")
        String email,

        String observacao,

        @Size(max = 150, message = "O endereço deve ter o máximo 150 caracteres.")
        String endereco,

        @Size(max = 20, message = "O número deve ter o máximo 20 caracteres.")
        String numero,

        @Size(max = 100, message = "O complemento deve ter o máximo 100 caracteres.")
        String complemento,

        @Size(max = 100, message = "A cidade deve ter o máximo 100 caracteres.")
        String cidade,

        @Size(max = 100, message = "O bairro deve ter o máximo 100 caracteres.")
        String bairro,

        @Size(max = 2, message = "O estado deve ter o máximo 2 caracteres.")
        String estado,

        @Size(max = 20, message = "O cep deve ter o máximo 20 caracteres.")
        String cep,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm

) {
    public Aluno toEntity(){
        Aluno aluno = new Aluno();
        preencher(aluno);
        return aluno;
    }

    public void preencher(Aluno aluno){
        aluno.setNome(nome);
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo(sexo);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setObservacao(observacao);
        aluno.setEndereco(endereco);
        aluno.setNumero(numero);
        aluno.setComplemento(complemento);
        aluno.setCidade(cidade);
        aluno.setBairro(bairro);
        aluno.setEstado(estado);
        aluno.setCep(cep);
        aluno.setCriadoEm(criadoEm);
        aluno.setAtualizadoEm(atualizadoEm);
    }






}
