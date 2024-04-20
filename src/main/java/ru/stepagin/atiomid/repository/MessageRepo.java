package ru.stepagin.atiomid.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.entity.MessageEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, UUID> {
    @Modifying
    @Transactional
    @Query("update MessageEntity m set m.text=:text where m.id=:id")
    void updateTextById(@Param("id") UUID id, @Param("text") String text);

    List<MessageEntity> findAllByTopicId(UUID topicId, PageRequest pageRequest);
}
