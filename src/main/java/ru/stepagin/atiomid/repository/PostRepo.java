package ru.stepagin.atiomid.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.entity.PostEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, UUID> {
    List<PostEntity> findAllByTopicId(UUID topicId);

    List<PostEntity> findAllByTopicId(UUID topicId, PageRequest pageRequest);

    @Modifying
    @Transactional
    @Query("delete from PostEntity p where p.message.id=:id")
    void deleteByMessageId(@Param("id") UUID uuid);
}
