package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.repositories.RefeicaoRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private UsuarioService usuarioService;

    public RefeicaoDTO makeDTO(Refeicao refeicao){
        PratoDTO prato = pratoService.getDishById(refeicao.getPratoID());
        RefeicaoDTO dto = new RefeicaoDTO(refeicao.getId(), refeicao.getMatriculaUser(), refeicao.getNomeUsuario(), refeicao.getDataReserva(), refeicao.getAvaliacaoQuant(), refeicao.getAvaliacaoComentario(), prato);
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
        Refeicao refeicao = new Refeicao(refeicaoDTO.getUsuarioMatricula(), refeicaoDTO.getUsuarioNome(), refeicaoDTO.getPrato().getId(), refeicaoDTO.getDataReserva());
        return makeDTO(saveRefeicao(refeicao));
    }

    public Refeicao saveRefeicao(Refeicao refeicao){
        return refeicaoRepository.save(refeicao);
    }

    @Transactional
    public void deleteRefeicao(Long idRefeicao){
        try{
            Optional<Refeicao> refeicaoOptional = refeicaoRepository.findById(idRefeicao);
            refeicaoOptional.ifPresent(refeicao -> {
                String matricula = refeicao.getMatriculaUser();
                usuarioService.devolverCredito(matricula);
                refeicaoRepository.delete(refeicao);
            });
        }
        catch (EmptyResultDataAccessException epda){
            throw new RecursoNaoEncontradoExcecao("Refeição com id: " + idRefeicao + "não encontrada");

        }
    }


    public List<RefeicaoDTO> listarRefeicoes() {
        List<Refeicao> bundle = refeicaoRepository.findAll();
        List<RefeicaoDTO> refeicoes = new ArrayList<>();
        for(Refeicao r : bundle){
            refeicoes.add(makeDTO(r));
        }
        return refeicoes;
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
