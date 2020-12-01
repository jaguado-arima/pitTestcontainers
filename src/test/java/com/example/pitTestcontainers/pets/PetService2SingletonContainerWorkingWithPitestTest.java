package com.example.pitTestcontainers.pets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class PetService2SingletonContainerWorkingWithPitestTest extends  AbstractPostgresContainerBaseTest{
    private static final String LASSIE = "Lassie";
    private static final String GOLDEN = "Golden retriever";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2020, 01, 01);

    @Autowired
    PetRepository petsRepo;

    PetService service;

    Pet pet1;

    @BeforeEach
    void setup() {
        service = new PetService(petsRepo);
        Pet pet = new Pet();
        pet.setName(LASSIE);
        pet.setType(GOLDEN);
        pet.setBirthDate(BIRTH_DATE);
        pet1 = petsRepo.save(pet);
    }

    @Test
    void shouldFindPetWithCorrectId() {
        Pet petFound = service.getPet(pet1.getId());
        assertEquals(LASSIE, petFound.getName());
        assertEquals(GOLDEN, petFound.getType());
        assertEquals(BIRTH_DATE, petFound.getBirthDate());
    }


    @Test
    void shouldInsertPetIntoDatabaseAndGenerateId() {
        LocalDate birthDate = LocalDate.now();
        Pet savedPet = service.savePet("Molly",birthDate,"Poodle" );

        assertTrue(savedPet.getId() != null);
        Pet petFound = service.getPet(savedPet.getId());
        assertEquals("Molly", petFound.getName());
        assertEquals("Poodle", petFound.getType());
        assertEquals(birthDate, petFound.getBirthDate());
    }


}
