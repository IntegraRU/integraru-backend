package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.repositories.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RefeicaoServiceImpl implements RefeicaoService{

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    public Refeicao cadastrarRefeicao(RefeicaoDTO refeicaoDTO) {
        Refeicao refeicao = new Refeicao(refeicaoDTO.getUsuario(),
                refeicaoDTO.getPrato(), refeicaoDTO.getData());

        return saveRefeicao(refeicao);
    }

    public Refeicao saveRefeicao(Refeicao refeicao) {
        return refeicaoRepository.save(refeicao);
    }

    public void deleteRefeicao(Refeicao refeicao) {
        refeicaoRepository.delete(refeicao);
    }

    public Optional<Refeicao> getRefeicaobyId(Long id) {
        return refeicaoRepository.findById(id);
    }

    public List<Refeicao> getRefeicoesByUsuario(String usuario) {
        return refeicaoRepository.findByUsuario(usuario);
    }

    public List<Refeicao> getRefeicoesByData(Date data) {
        return refeicaoRepository.findByData(data);
    }

    public List<Refeicao> getRefeicoes() {
        return refeicaoRepository.findAll();
    }

    public Refeicao avaliarRefeicao(Refeicao refeicao, int avaliacaoQuantitativa, String avaliacaoComentario) {
        refeicao.setAvaliacaoQuant(avaliacaoQuantitativa);
        refeicao.setAvaliacaoComentario(avaliacaoComentario);
        return saveRefeicao(refeicao);
    }

    public boolean estaReservado(RefeicaoDTO refeicaoDTO) {
        List<Refeicao> refeicoes = getRefeicoesByUsuario(refeicaoDTO.getUsuario());

        for(Refeicao refeicao: refeicoes){
            LocalDate dataRefeicao = refeicao.getDataReserva();
            LocalDate dataReserva = refeicaoDTO.getData();
            if(dataRefeicao.getDayOfYear() == dataReserva.getDayOfYear() &&
                    dataRefeicao.getYear() == dataReserva.getYear() &&
                    refeicao.getPrato().equals(refeicaoDTO.getPrato())){
                return true;
            }
        }
        return false;
    }


    public Refeicao efetuarCheckout(CheckoutDTO checkoutDTO, Refeicao refeicao) {
        refeicao.setDataCheckout(checkoutDTO.getDataCheckout());
        return saveRefeicao(refeicao);
    }

    public BigDecimal getValorRefeicao(Refeicao refeicao) {
        //TODO
        return null;
    }
}
