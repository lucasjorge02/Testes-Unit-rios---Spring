package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Spy;
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
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TutorService service;

    @Test
    void cadastrarTutorComDadosCorretos() throws Exception{
        String json = """
                {
                "nome": "Lucas Barroso",
                "telefone": "(11)91234-5429",
                "email": "email@example.com"
                }
                """;
        var response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(200, response.getStatus());
    }

    @Test
    void naoCadastrarTutor() throws Exception{
        String json = """
                {}
                """;
        var response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(400, response.getStatus());
    }

    @Test
    void naoDeveriaCadastrarTutorRepetido() throws Exception{
        CadastroTutorDto dto = new CadastroTutorDto("Lucas Barroso", "(11)91234-5429", "email@example.com");
        String json = """
                {
                "nome": "Lucas Barroso",
                "telefone": "(11)91234-5429",
                "email": "email@example.com"
                }
                """;
        BDDMockito.doThrow(ValidacaoException.class).when(service).cadastrar(dto);

        var response = mockMvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaAtualizarDadosDoTutor() throws Exception {
        String json = """
                {
                  "id": 1,
                  "nome": "Matheus Ferreira",
                  "telefone": "(11)94321-9876",
                  "email": "xpto@second.time"
                }
                """;
        var response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(200, response.getStatus());
    }

    @Test
    void naoDeveriaAtualizarDadosDoTutor() throws Exception {
        String json = """
                {}
                """;
        var response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(400, response.getStatus());
    }

    @Test
    void naoDeveriaAtualizarDadosDoTutorPorErroNaValidacao() throws Exception {
        String json = """
                {
                  "id": 1,
                  "nome": "Matheus Ferreira",
                  "telefone": "(11)94321-9876",
                  "email": "xpto@second.time"
                }
                """;
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l, "Matheus Ferreira", "(11)94321-9876","xpto@second.time");

        BDDMockito.doThrow(ValidacaoException.class).when(service).atualizar(dto);
        var response = mockMvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(400, response.getStatus());
    }
}