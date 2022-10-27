package com.telegrambot.jd501.service.DogService;

import com.telegrambot.jd501.Exceptions.PetNotFoundException;
import com.telegrambot.jd501.model.dog.Dog;
import com.telegrambot.jd501.repository.Dog.DogRepository;
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
class DogServiceTest {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogService out;

    @Test
    void getAllDog() {
        when(dogRepository.findAll()).thenReturn(DOG_LIST);

        assertEquals(out.getAllPets(), DOG_LIST);
    }

    @Test
    void createDog() {
        when(dogRepository.save(any(Dog.class))).thenReturn(DOG_1);

        assertEquals(out.createPet(DOG_1), DOG_1);
    }

    @Test
    void updateDog() {
        when(dogRepository.findById(anyLong())).thenReturn(Optional.of(DOG_1));
        when(dogRepository.save(any(Dog.class))).thenReturn(DOG_11);

        assertEquals(out.updatePet(DOG_11), DOG_11);
    }

    @Test
    void deleteDog() {
        when(dogRepository.findById(anyLong())).thenReturn(Optional.of(DOG_1));

        assertEquals(out.deletePet(ID1), DOG_1);
    }
    @Test
    void petNotFoundExceptionTest1() {
        when(dogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.deletePet(ID2));
    }

    @Test
    void petNotFoundExceptionTest2() {
        when(dogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class,() ->out.updatePet(DOG_2));
    }
}