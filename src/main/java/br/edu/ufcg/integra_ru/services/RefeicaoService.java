package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.repositories.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new RefeicaoDTO(refeicao.getMatriculaUser(), refeicao.getDataReserva(), refeicao.getAvaliacaoQuant(), refeicao.getAvaliacaoComentario(), prato);

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
}
