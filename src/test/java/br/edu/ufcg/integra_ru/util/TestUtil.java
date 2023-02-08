package br.edu.ufcg.integra_ru.util;


import br.edu.ufcg.integra_ru.dtos.*;
import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.TipoPrato;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    public static final String PASSWORD = "password";

    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static String convertObjectToString(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public static String makeRandomString(int size) {
        return RandomStringUtils.random(size);
    }

    public static long makeRandomNumber(int limite) {
        Random rand = new Random();
        return rand.nextInt(limite) + 1;
    }

    public static UsuarioDTO makeRandomUser() {
        UsuarioDTO user = new UsuarioDTO();
        user.setMatricula("119210094");
        user.setNome(makeRandomString(15));
        user.setEmail(makeRandomString(20));
        user.setTelefone(makeRandomString(9));
        user.setSenha(makeRandomString(12));
        user.setUrlImagem(makeRandomString(30));
        return user;
    }

    public static PratoDTO makeRandomPrato(){
        PratoDTO prato = new PratoDTO();
        prato.setData(LocalDate.of(2023, 02, 02));
        prato.setModalidadePrato(ModalidadePrato.ALMOCO);
        prato.setTipo(TipoPrato.COMUM);
        prato.setNome(makeRandomString(10));
        prato.setItens(makeRandomString(30));

        return prato;
    }

    public static RefeicaoDTO makeRefeicaoOf(PratoDTO prato, UsuarioDTO user){

        RefeicaoDTO refeicao = new RefeicaoDTO((Long) makeRandomNumber(1000),
                user.getMatricula(),
                user.getNome(),
                LocalDateTime.now(),
                0, "",
                prato);

        return refeicao;
    }

    public static AvaliacaoDTO makeRandomAvaliacao(){
        AvaliacaoDTO dto = new AvaliacaoDTO();
        dto.setAvaliacaoComentario(makeRandomString(30));
        dto.setAvaliacaoQuantitativa((int) makeRandomNumber(5));

        return dto;
    }

    public static CheckoutDTO makeCheckoutDTOOf(RefeicaoDTO refeicao){
        CheckoutDTO dto = new CheckoutDTO();
        dto.setRefeicaoID(refeicao.getRefeicaoID());
        dto.setDataCheckout(LocalDateTime.now());
        dto.setMatriculaUser(refeicao.getUsuarioMatricula());

        return dto;
    }

    public static PatchUserCreditDTO makeRandomPatchUserCredit(){
        PatchUserCreditDTO dto = new PatchUserCreditDTO();
        dto.setCredito((double) makeRandomNumber(100));
        return dto;
    }

    public static PatchUserDTO makeRandomPatchUser(){
        PatchUserDTO dto = new PatchUserDTO();
        dto.setEmail(makeRandomString(15));
        dto.setNome(makeRandomString(20));
        dto.setTelefone(makeRandomString(9));
        dto.setUrlImagem(makeRandomString(20));

        return dto;
    }

}
