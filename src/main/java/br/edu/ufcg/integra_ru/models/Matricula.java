package br.edu.ufcg.integra_ru.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matricula")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Matricula {

    @Id
    private String valorMatricula;

    private boolean beneficiario = false;

    
}
