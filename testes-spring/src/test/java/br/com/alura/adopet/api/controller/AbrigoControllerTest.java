package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    @Test
    void deveriaRetornar200AoListarAbrigos() throws Exception {
        var response = mockMvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoCadastrarAbrigo() throws Exception {
        String json = """
                {
                  "nome": "Lucas Barroso",
                  "telefone": "(11)96635-4758",
                  "email": "xpto@first.time"
                }
                """;

        var response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }




    @Test
    void deveriaRetornar400AoCadastrarAbrigo() throws Exception {
        String json = """
                {}
                """;

        var response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarAbrigoELancarException() throws Exception {
        String json = """
                {
                  "nome": "Lucas Barroso",
                  "telefone": "(11)96635-4758",
                  "email": "xpto@first.time"
                }
                """;
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Lucas Barroso", "(11)96635-4758", "xpto@first.time");
        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).cadatrar(dto);

        var response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }


    @Test
    void deveriaRetornar200AoListarAbrigosPorNome() throws Exception {
       String nome = "Pet Love";

        var response = mockMvc.perform(
                get("/abrigos/{nome}/pets",nome)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoListarAbrigosPorId() throws Exception {
        String id = "1";

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400ListarPetsPorId() throws Exception {
        String id = "1";

        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).listarPetsDoAbrigo(id);

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar404ListarPetsPorNome() throws Exception {
        String id = "Pets Love";

        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).listarPetsDoAbrigo(id);

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar404ListarPetsPorId() throws Exception {
        String id = "1";

        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).listarPetsDoAbrigo(id);

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoCadastrarPetEmAbrigoPorNome() throws Exception {
        String nome = "Pet Love";
        String json = """
                {
                  "tipo": "GATO",
                  "nome": "Teemo",
                  "raca": "Siames",
                  "idade": 3,
                  "cor": "Bege",
                  "peso": 7.00
                }
                """;

        var response = mockMvc.perform(
                post("/abrigos/{nome}/pets",nome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarPetEmAbrigoPorNome() throws Exception {
        String nome = "Pet Love";
        String json = """
                {}
                """;

        var response = mockMvc.perform(
                post("/abrigos/{nome}/pets",nome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornar200AoCadastrarPetEmAbrigoPorId() throws Exception {
        String id = "1";
        String json = """
                {
                  "tipo": "GATO",
                  "nome": "Teemo",
                  "raca": "Siames",
                  "idade": 3,
                  "cor": "Bege",
                  "peso": 7.00
                }
                """;

        var response = mockMvc.perform(
                post("/abrigos/{id}/pets",id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornar400AoCadastrarPetEmAbrigoPorId() throws Exception {
        String id = "1";
        String json = """
                {}
                """;

        var response = mockMvc.perform(
                post("/abrigos/{id}/pets",id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }


    @Test
    void deveriaRetornar404AoCadastrarPetEmAbrigoPorIdComIdInvalido() throws Exception {
        String id = "1";
        String json = """
                {
                  "tipo": "GATO",
                  "nome": "Teemo",
                  "raca": "Siames",
                  "idade": 3,
                  "cor": "Bege",
                  "peso": 7.00
                }
                """;
        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).carregarAbrigo(id);

        var response = mockMvc.perform(
                post("/abrigos/{id}/pets",id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornar404AoCadastrarPetEmAbrigoPorNomeComNomeInvalido() throws Exception {
        String id = "Pet Lovers";
        String json = """
                {
                  "tipo": "GATO",
                  "nome": "Teemo",
                  "raca": "Siames",
                  "idade": 3,
                  "cor": "Bege",
                  "peso": 7.00
                }
                """;
        BDDMockito.doThrow(ValidacaoException.class).when(abrigoService).carregarAbrigo(id);

        var response = mockMvc.perform(
                post("/abrigos/{id}/pets",id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

}