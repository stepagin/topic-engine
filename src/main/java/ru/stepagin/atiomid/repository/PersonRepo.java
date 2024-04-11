package ru.stepagin.atiomid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepagin.atiomid.entity.PersonEntity;

@Repository
public interface PersonRepo extends JpaRepository<PersonEntity, String> {
    boolean existsByName(String author);
}
