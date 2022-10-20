package com.telegrambot.jd501.service;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.model.Pet;
import com.telegrambot.jd501.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.service.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService out;

    @Test
    void getAllPetTest() {
        when(petRepository.findAll()).thenReturn(PET_LIST);

        assertEquals(out.getAllPet(), PET_LIST);

    }

    @Test
    void createPetTest() {
        when(petRepository.save(any(Pet.class))).thenReturn(PET_1);

        assertEquals(out.createPet(PET_1), PET_1);
    }

    @Test
    void updatePetTest() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(PET_1));
        when(petRepository.save(PET_11)).thenReturn(PET_11);

        assertEquals(out.updatePet(PET_11), PET_11);
    }

    @Test
    void deletePetTest(){
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(PET_1));

        assertEquals(out.deletePet(ID1), PET_1);
    }

    @Test
    void petNotFoundExceptionTest1() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.deletePet(anyLong()));
    }
    @Test
    void petNotFoundExceptionTest2() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.updatePet(PET_2));
    }
}