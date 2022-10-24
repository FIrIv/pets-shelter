package com.telegrambot.jd501.service.CatService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.repository.Cat.CatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {
    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatService out;

    @Test
    void getAllCat() {
        when(catRepository.findAll()).thenReturn(CAT_LIST);

        assertEquals(out.getAllPets(), CAT_LIST);
    }

    @Test
    void createCat() {
        when(catRepository.save(any(Cat.class))).thenReturn(CAT_1);

        assertEquals(out.createPet(CAT_1), CAT_1);
    }

    @Test
    void updateCat() {
        when(catRepository.findById(anyLong())).thenReturn(Optional.of(CAT_1));
        when(catRepository.save(any(Cat.class))).thenReturn(CAT_11);

        assertEquals(out.updatePet(CAT_11), CAT_11);
    }

    @Test
    void deleteCat() {
        when(catRepository.findById(anyLong())).thenReturn(Optional.of(CAT_1));

        assertEquals(out.deletePet(ID1), CAT_1);
    }
    @Test
    void petNotFoundExceptionTest1() {
        when(catRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.deletePet(ID2));
    }

    @Test
    void petNotFoundExceptionTest2() {
        when(catRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.updatePet(CAT_2));
    }
}