package ru.stepagin.atiomid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepagin.atiomid.entity.TopicEntity;

import java.util.UUID;

@Repository
public interface TopicRepo extends JpaRepository<TopicEntity, UUID> {
}
