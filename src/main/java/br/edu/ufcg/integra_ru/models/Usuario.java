package br.edu.ufcg.integra_ru.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String matricula;

    @NotNull()
    private String nome;

    @NotNull()
    private String email;

    private String telefone;

    private String urlImagem;

    public Usuario() {

    }

    public Usuario(String matricula, String nome, String email, String telefone, String urlImagem) {
        super();
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.urlImagem = urlImagem;
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

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

}
