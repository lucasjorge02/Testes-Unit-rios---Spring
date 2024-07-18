package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;
    @Mock
    private TutorRepository repository;
    @Mock
    private CadastroTutorDto tutorDto;
    @Mock
    private AtualizacaoTutorDto atualizaDto;
    @Mock
    private Tutor tutor;

    @Test
    void naoDeveriaCadastrarTutorCadastrado() {
        given(repository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> tutorService.cadastrar(tutorDto));
    }

    @Test
    void deveriaCadastrarTutor() {
        given(repository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(false);

        assertDoesNotThrow(() -> tutorService.cadastrar(tutorDto));
        then(repository).should().save(new Tutor(tutorDto));
    }

    @Test
    void atualizar() {
        given(repository.getReferenceById(atualizaDto.id())).willReturn(tutor);

        tutorService.atualizar(atualizaDto);
        then(tutor).should().atualizarDados(atualizaDto);
    }
}