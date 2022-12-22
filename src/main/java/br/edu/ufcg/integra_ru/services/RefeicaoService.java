package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.repositories.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefeicaoService {

    @Autowired
    RefeicaoRepository refeicaoRepository;

    @Autowired
    PratoService pratoService;

    public RefeicaoDTO makeDTO(Refeicao refeicao){
        PratoDTO prato = pratoService.getDishById(refeicao.getPratoID());
        RefeicaoDTO dto = new RefeicaoDTO(refeicao.getId(), refeicao.getMatriculaUser(), refeicao.getDataReserva(), refeicao.getAvaliacaoQuant(), refeicao.getAvaliacaoComentario(), prato);
        if(refeicao.getDataCheckout() != null){
            dto.setDataCheckout(refeicao.getDataCheckout());
        }
        return dto;
    }

    public boolean refeicaoExiste(RefeicaoDTO refeicaoDTO) {
        List<Refeicao> refeicoes = refeicaoRepository.findByMatriculaUser(refeicaoDTO.getUsuarioMatricula());
        for(Refeicao r : refeicoes){
            if(r.getDataReserva().getDayOfYear() == refeicaoDTO.getDataReserva().getDayOfYear()){
                return true;
            }
        }
        return false;
    }

    public RefeicaoDTO cadastrarRefeicao(RefeicaoDTO refeicaoDTO) {
        Refeicao refeicao = new Refeicao(refeicaoDTO.getUsuarioMatricula(), refeicaoDTO.getPrato().getId(), refeicaoDTO.getDataReserva());
        return makeDTO(saveRefeicao(refeicao));
    }

    public Refeicao saveRefeicao(Refeicao refeicao){
        return refeicaoRepository.save(refeicao);
    }

    public void deleteRefeicao(Long idRefeicao){
        Optional<Refeicao> refeicaoOptional = refeicaoRepository.findById(idRefeicao);
        refeicaoOptional.ifPresent(refeicao -> refeicaoRepository.delete(refeicao));
    }


    public Page<RefeicaoDTO> listarRefeicoes(Pageable pageable) {
        return refeicaoRepository.findAll(pageable).map(refeicao -> makeDTO(refeicao));
    }

    public Optional<RefeicaoDTO> getRefeicaoById(Long id) {
        Optional<Refeicao> refeicao = refeicaoRepository.findById(id);
        return refeicao.map(this::makeDTO);
    }

    public RefeicaoDTO avaliarRefeicao(Long id, AvaliacaoDTO avaliacaoDTO) {
        Optional<Refeicao> refeicaoOptional = refeicaoRepository.findById(id);
        if(refeicaoOptional.isPresent()){
            Refeicao refeicao = refeicaoOptional.get();
            refeicao.setAvaliacaoQuant(avaliacaoDTO.getAvaliacaoQuantitativa());
            if(avaliacaoDTO.getAvaliacaoComentario() != null && !avaliacaoDTO.getAvaliacaoComentario().equals("")){
                refeicao.setAvaliacaoComentario(avaliacaoDTO.getAvaliacaoComentario());
            }
            return makeDTO(saveRefeicao(refeicao));
        }
        return null;
    }

    public boolean fezCheckout(Long refeicaoID) {
        Optional<Refeicao> opt =  refeicaoRepository.findById(refeicaoID);
        if(opt.isPresent()){
            return opt.get().getDataCheckout() != null;
        }
        return false;
    }

    public RefeicaoDTO efetuarCheckout(CheckoutDTO checkoutDTO) {
        Optional<Refeicao> opt =  refeicaoRepository.findById(checkoutDTO.getRefeicaoID());
        if(opt.isPresent()){
            Refeicao refeicao = opt.get();
            refeicao.setDataCheckout(checkoutDTO.getDataCheckout());
            refeicaoRepository.save(refeicao);
            return makeDTO(saveRefeicao(refeicao));
        }
        return null;
    }

    public BigDecimal getValorRefeicao(Long id) {
        Optional<Refeicao> opt =  refeicaoRepository.findById(id);
        if(opt.isPresent()){
            Refeicao refeicao = opt.get();
            PratoDTO prato = pratoService.getDishById(refeicao.getPratoID());
            return prato.getModalidadePrato().getValue();
        }
        return null;
    }


}
