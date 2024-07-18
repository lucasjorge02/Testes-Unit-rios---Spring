package br.com.alura.adopet.api.service;


import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
    @InjectMocks
    private AbrigoService abrigoService;
    @Mock
    private AbrigoRepository abrigoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private CadastroAbrigoDto dto;
    @Mock
    private Abrigo abrigo;

    @Test
    void listarTodosOsAbrigos() {
        // Act
        abrigoService.listar();
        // Asserts
        then(abrigoRepository).should().findAll();
    }

    @Test
    void naoCadatrarAbrigoPoisTemCadastrado() {
        //ARRANGE
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(),dto.email())).willReturn(true);

        //ASSERTS + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.cadatrar(dto));
    }

    @Test
    void cadatrarAbrigoPoisNaoTemCadastro() {
        //ARRANGE
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(),dto.email())).willReturn(false);

        //ASSERTS + ACT
        Assertions.assertDoesNotThrow(() -> abrigoService.cadatrar(dto));
    }

    @Test
    void listarPetsDoAbrigoPassandoIdDoAbrigo() {
        String id = "1234";
        given(abrigoRepository.findById(any())).willReturn(Optional.of(abrigo));

        abrigoService.listarPetsDoAbrigo(id);

        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void listarPetsDoAbrigoPassandoNomeDoAbrigo() {
        String nome = "Pet Crazy";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        abrigoService.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void carregarAbrigoPorID() {
        String id = "1234";
        given(abrigoRepository.findById(any())).willReturn(Optional.of(abrigo));

        abrigoService.carregarAbrigo(id);

        then(abrigoRepository).should().findById(Long.valueOf(id));
    }

    @Test
    void carregarAbrigoPorNome() {
        String nome = "Pet Love";
        given(abrigoRepository.findByNome(any())).willReturn(Optional.of(abrigo));

        abrigoService.carregarAbrigo(nome);

        then(abrigoRepository).should().findByNome(nome);
    }

    @Test
    void naoCarregarAbrigoPorID() {
        String id = "1234";
        given(abrigoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () ->  abrigoService.carregarAbrigo(id));
        then(abrigoRepository).should().findById(Long.valueOf(id));
    }

    @Test
    void naoCarregarAbrigoPorNome() {
        String nome = "Pet Love";
        given(abrigoRepository.findByNome(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () ->  abrigoService.carregarAbrigo(nome));
        then(abrigoRepository).should().findByNome(nome);
    }
}