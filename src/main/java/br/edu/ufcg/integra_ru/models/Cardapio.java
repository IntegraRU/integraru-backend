package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"nome"})
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCardapio tipo;

    private String nome;

    private String urlImagem;

    @OneToMany(mappedBy = "cardapio", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<ItemCardapio> itens = new HashSet<>();

    public void clearItens(){
        itens.clear();
    }

    public void addItem(ItemCardapio item){
        itens.add(item);
        item.setCardapio(this);
    }
}
