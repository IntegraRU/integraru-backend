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



        double media = (double) sumAvaliacoes / avaliacoesQuant.size();
        double taxaAvaliacao = (avaliacoesQuant.size() / (double) comensais) * 100;
        double taxaComentario = (avaliacoesComentario.size() / (double) comensais) * 100;
        double taxaOptantes = ((double) refeicoes.size() / (refeicaoRepository.findAll().size()) * 100);

        return new RelatorioDTO(media, avaliacoesQuant, avaliacoesComentario, checkouts, comensais, taxaAvaliacao, taxaComentario, taxaOptantes);
    }

    public List<RelatorioDTO> gerarRelatorio(){
        List<Prato> pratos = pratoRepository.findAll();
        List<RelatorioDTO> relatorios = Collections.emptyList();
        pratos.forEach(prato -> relatorios.add(gerarRelatorio(prato.getId())));
        return relatorios;

    }
}
