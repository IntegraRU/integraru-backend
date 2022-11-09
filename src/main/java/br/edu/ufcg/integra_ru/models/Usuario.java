package br.edu.ufcg.integra_ru.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String matricula;

    @NotNull()
    private String nome;

    @NotNull()
    private String email;

    private String telefone;

    public Usuario() {

    }

    public Usuario(String matricula, String nome, String email, String telefone) {
        super();
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return getMatricula().equals(usuario.getMatricula()) && getEmail().equals(usuario.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatricula(), getEmail());
    }
}
