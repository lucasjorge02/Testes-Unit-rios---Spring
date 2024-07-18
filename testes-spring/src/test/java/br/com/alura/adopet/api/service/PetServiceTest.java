package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository repository;
    @Mock
    private Abrigo abrigo;
    @Mock
    private CadastroPetDto dto;

    @Test
    void buscarPetsDisponiveis() {

        petService.buscarPetsDisponiveis();

        then(repository).should().findAllByAdotadoFalse();
    }

    @Test
    void cadastrarPet() {
        petService.cadastrarPet(abrigo, dto);

        then(repository).should().save(new Pet(dto, abrigo));
    }
}