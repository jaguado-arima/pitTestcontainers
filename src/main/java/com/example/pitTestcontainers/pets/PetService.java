package com.example.pitTestcontainers.pets;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        super();
        this.repository = repository;
    }

    public List<Pet> getPets() {
        Iterable<Pet> pets = repository.findAll();
        return StreamSupport
                .stream(pets.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Pet getPet(int petId) {
       return repository.findById(petId).orElse(null);
    }

    public Pet savePet(String name, LocalDate birthDate, String type) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setType(type);
        return this.repository.save(pet);
    }
}
