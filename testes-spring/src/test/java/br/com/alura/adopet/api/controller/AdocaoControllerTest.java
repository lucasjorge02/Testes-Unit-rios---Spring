package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService adocaoService;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """;

        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocao() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idPet": 1l,
                    "idTutor": 1l,
                    "motivo": "Motivo qualquer"
                }
                """;
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l,1l,"Motivo qualquer");
        BDDMockito.doThrow(ValidacaoException.class).when(adocaoService).solicitar(dto);
        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoAprovarAdocao() throws Exception {
        String json = """
                {
                  "idAdocao": 1
                }
                """;
        var response = mvc.perform(
                put("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoAprovarAdocao() throws Exception {
        String json = """
                {}
                """;
        var response = mvc.perform(
                put("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoReprovarAdocao() throws Exception {
        String json = """
                {
                  "idAdocao": 1,
                  "justificativa": "Por que sim"
                }
                """;
        var response = mvc.perform(
                put("/adocoes/reprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoReprovarAdocao() throws Exception {
        String json = """
                {}
                """;
        var response = mvc.perform(
                put("/adocoes/reprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}