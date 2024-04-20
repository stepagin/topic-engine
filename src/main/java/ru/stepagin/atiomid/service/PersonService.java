package ru.stepagin.atiomid.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.stepagin.atiomid.DTO.PersonDTO;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.repository.PersonRepo;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private ObjectMapper jacksonObjectMapper;
    @Value("${app.redis.host}")
    private String redisHost;
    @Value("${app.redis.port}")
    private int redisPort;
    private final JedisPool jedisPool = new JedisPool(redisHost, redisPort);


    public PersonEntity getPerson(String name) {
        return getCachedPerson(name);
    }

    private PersonEntity getCachedPerson(String name) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "person:" + name;
            String raw = jedis.get(key);
            if (raw != null) {
                return jacksonObjectMapper.readValue(raw, PersonEntity.class);
            }
            PersonEntity person = personRepo.findById(name).orElse(null);
            if (person == null) {
                return null;
            }
            jedis.setex(key, 10, jacksonObjectMapper.writeValueAsString(person));
            return person;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
