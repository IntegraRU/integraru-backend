package br.edu.ufcg.integra_ru.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    private String urlImagem;

    private String senha;

    private Double credito;

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

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    // @JsonIgnore
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }
}
