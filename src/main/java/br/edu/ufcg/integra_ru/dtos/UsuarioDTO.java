package br.edu.ufcg.integra_ru.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UsuarioDTO {


    @NotNull(message = "nome é obrigatório")
    @NotEmpty(message = "nome é obrigatório")
    private String matricula;

    @NotNull(message = "nome é obrigatório")
    @NotEmpty(message = "nome é obrigatório")
    private String nome;

    @NotNull(message = "email é obrigatório")
    private String email;

    private String telefone;

    public UsuarioDTO(String matricula, String nome, String email, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public UsuarioDTO(String matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
