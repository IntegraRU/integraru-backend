package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    private String authority;


}
