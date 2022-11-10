package br.edu.ufcg.integra_ru.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private LocalDate dataCardapio;

    @OneToMany(mappedBy = "cardapio", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PratoCardapio> pratos = new ArrayList<>();

    public void addDish(Prato prato){
        pratos.add(new PratoCardapio(this, prato));
    }

    public void removeDish(PratoCardapio dish){
        pratos.remove(dish);
    }

    @Override
    public String toString() {
        return "Cardapio{" +
                "id=" + id +
                ", dataCardapio=" + dataCardapio +
                ", pratos=" + pratos +
                '}';
    }
}
