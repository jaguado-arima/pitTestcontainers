package com.example.pitTestcontainers.pets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class PetService2SingletonContainerWorkingWithPitestTest extends AbstractPostgresContainerBaseTest {

    @Autowired
    PetRepository petsRepo;

    PetService service;

    @BeforeEach
    void setup() {
        service = new PetService(petsRepo);
    }

    @Test
    void shouldInsertPetIntoDatabaseAndGenerateId() {
        LocalDate birthDate = LocalDate.now();
        Pet savedPet = service.savePet("Molly", birthDate, "Poodle");

        Pet petFound = service.getPet(savedPet.getId());
        assertTrue(savedPet.getId() != null);
        assertAll("Pet has been saved in database",
                () -> assertEquals("Molly", petFound.getName()),
                () -> assertEquals("Poodle", petFound.getType()),
                () -> assertEquals(birthDate, petFound.getBirthDate())
        );
    }


}
