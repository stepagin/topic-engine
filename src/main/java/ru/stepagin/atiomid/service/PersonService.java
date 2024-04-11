package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.repository.PersonRepo;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    public PersonEntity createPerson(String name) {
        if (personRepo.existsByName(name)) {
            throw new InvalidIdSuppliedException("Person with name " + name + " already exists");
        }
        if (name.length() < 4) {
            throw new InvalidIdSuppliedException("Name is too short");
        }
        PersonEntity p = new PersonEntity();
        p.setName(name);
        p.setCreatedDate(OffsetDateTime.now(ZoneId.of("Europe/Moscow")));
        p = personRepo.save(p);
        return p;
    }

    public PersonEntity getPerson(String author) {
        return personRepo.findById(author).orElse(null);
    }
}
