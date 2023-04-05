package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private String matricula;

    private String nome;

    private String email;

    private String telefone;

    private String urlImagem;

    private Double credito;

    private boolean beneficiario;

    public UsuarioResponseDTO(Usuario entity) {
        this.matricula = entity.getMatricula();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.telefone = entity.getTelefone();
        this.urlImagem = entity.getUrlImagem();
        this.credito = entity.getCredito();
        this.beneficiario = entity.isBeneficiario();
    }

    public UsuarioResponseDTO(String matricula, String nome, String email, String telefone, String urlImagem) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.urlImagem = urlImagem;
    }
}
