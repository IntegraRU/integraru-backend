package br.edu.ufcg.integra_ru.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id", "nome"})
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPrato tipo;

    @Enumerated(EnumType.STRING)
    private ModalidadePrato modalidadePrato;

    private String nome;

    private String urlImagem;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Cardapio cardapio;

    private String itens;

}
