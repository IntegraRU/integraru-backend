package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.RelatorioDTO;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.repositories.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    RefeicaoRepository refeicaoRepository;
    @Autowired
    PratoRepository pratoRepository;

    public RelatorioDTO gerarRelatorio(Long pratoID){

        List<Refeicao> refeicoes = refeicaoRepository.findByPratoID(pratoID);

        int sumAvaliacoes = 0;
        int comensais = 0;
        List<Integer> avaliacoesQuant = new ArrayList<Integer>();
        List<String> avaliacoesComentario = new ArrayList<>();
        List<LocalDateTime> checkouts = new ArrayList<>();

        for(Refeicao r : refeicoes){
            if(r.getDataCheckout() != null){
                comensais++;
                checkouts.add(r.getDataCheckout());
                if(r.getAvaliacaoQuant() > 0){
                    avaliacoesQuant.add(r.getAvaliacaoQuant());
                    sumAvaliacoes += r.getAvaliacaoQuant();
                }
                if(r.getAvaliacaoComentario() != null && !r.getAvaliacaoComentario().equals("")){
                    avaliacoesComentario.add(r.getAvaliacaoComentario());
                }
            }
        }

        double media = (double) sumAvaliacoes / sizeOr1(avaliacoesQuant);
        double taxaAvaliacao = (avaliacoesQuant.size() / (double) return1If0(comensais)) * 100;
        double taxaComentario = (avaliacoesComentario.size() / (double) return1If0(comensais)) * 100;

        int numRefeicoesTotais = refeicaoRepository.findAll().size();
        double taxaOptantes = ((double) refeicoes.size() / (return1If0(numRefeicoesTotais)) * 100);

        return new RelatorioDTO(media, avaliacoesQuant, avaliacoesComentario, checkouts, comensais, taxaAvaliacao, taxaComentario, taxaOptantes);
    }

    public List<RelatorioDTO> gerarRelatorio(){
        List<Prato> pratos = pratoRepository.findAll();
        List<RelatorioDTO> relatorios = new ArrayList<>();
        pratos.forEach(prato -> {
            RelatorioDTO relatorioDTO = gerarRelatorio(prato.getId());
            if(relatorioDTO != null){
              relatorios.add(relatorioDTO);
            }
        });
        return relatorios;

    }

    private<T> int sizeOr1(List<T> list) {
        if(list == null) return 1;
        return list.size() != 0 ? list.size() : 1;
    }

    private int return1If0(int value){
        return value == 0 ? 1 : value;
    }
}
