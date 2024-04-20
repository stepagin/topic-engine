package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.DTO.PersonDTO;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.exception.ValidationException;
import ru.stepagin.atiomid.service.PersonService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/auth")
public class AuthController {
    @Autowired
    PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<PersonDTO> register(@RequestBody PersonEntity entity) {
        if (entity.getName() == null || entity.getPassword() == null) {
            throw new ValidationException("Both name and password are required");
        }
        return ResponseEntity.ok(personService.registerPerson(entity.getName().trim(), entity.getPassword()));
    }
}
