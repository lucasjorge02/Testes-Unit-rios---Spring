package br.com.alura.adopet.api.controller;


import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService service;

    @Test
    void listarTodosDisponiveis() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}