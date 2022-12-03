package br.edu.ufcg.integra_ru.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    @OneToMany(mappedBy = "cardapio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Prato> pratos = new ArrayList<>();

    public void addDish(Prato prato){
        if(!pratos.contains(prato)){
            pratos.add(prato);
            prato.setCardapio(this);
        }
    }

    public boolean containsDish(Prato prato){
        return pratos.contains(prato);
    }

    public void removeDish(Prato prato) {
        pratos.remove(prato);
    }
}
