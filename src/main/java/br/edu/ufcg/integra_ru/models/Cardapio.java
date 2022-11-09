package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCardapio tipo;

    private String nome;

    private String urlImagem;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "itens_prato", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "nome")
    private Set<String> itens = new HashSet<>();

    public void clearItens(){
        itens.clear();
    }

    public void addItem(String item){
        itens.add(item);
    }
}
