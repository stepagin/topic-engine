package ru.stepagin.atiomid.service;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepagin.atiomid.DTO.PersonDTO;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.repository.PersonRepo;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    public PersonEntity getPerson(String author) {
        return personRepo.findById(author).orElse(null);
    }

    public PersonDTO registerPerson(@Nonnull String name, @Nonnull String password) {
        if (name.length() < 4) {
            throw new IllegalArgumentException("Login must contain at least 4 characters");
        } else if (password.length() < 6) {
            throw new IllegalArgumentException("Password must contain at least 6 characters");
        }
        if (personRepo.existsById(name)) {
            throw new IllegalArgumentException("User with name " + name + " already exists");
        }
        PersonEntity personEntity = new PersonEntity(name, password);
        personRepo.save(personEntity);
        return new PersonDTO(personEntity);
    }
}
