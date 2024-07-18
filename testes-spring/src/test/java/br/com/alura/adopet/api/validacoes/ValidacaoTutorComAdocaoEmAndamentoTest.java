package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;
    @Spy
    private List<Adocao> adocoes = new ArrayList<>();
    @Mock
    private Tutor tutor;
    @Mock
    private Pet pet;

    private Adocao adocao;

    @Test
    void deveriaPermitirAdocao() {
        // ARRANGE
        this.adocao = new Adocao(tutor, pet, "Motivo qualquer");
        adocao.marcarComoAprovada();
        adocoes.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        // ACT + ASSERTS
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirAdocao() {
        // ARRANGE
        this.adocao = new Adocao(tutor, pet, "Motivo qualquer");
        adocoes.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        // ACT + ASSERTS
        Assertions.assertThrows(ValidacaoException.class,() -> validacao.validar(dto));
    }

}