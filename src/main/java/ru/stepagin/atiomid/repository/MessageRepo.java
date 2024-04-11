package ru.stepagin.atiomid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepagin.atiomid.entity.MessageEntity;

import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, UUID> {
}
