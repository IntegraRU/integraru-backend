package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.Matricula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {

    private String matricula;

    private boolean beneficiario = false;


    public MatriculaDTO(Matricula entity) {
        matricula = entity.getValorMatricula();
        beneficiario = entity.isBeneficiario();
    }
}
