package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.EAGER) // 1
    @CollectionTable(name = "itens_prato", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "nome") // 3
    private List<String> itens;
}
