package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"nome"})
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPrato tipo;

    private String nome;

    private String urlImagem;

    @OneToMany(mappedBy = "prato", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<ItemPrato> itens = new HashSet<>();

    public void clearItens(){
        itens.clear();
    }

    public void addItem(ItemPrato item){
        itens.add(item);
        item.setPrato(this);
    }
}
