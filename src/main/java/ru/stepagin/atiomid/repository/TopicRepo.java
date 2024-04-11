package ru.stepagin.atiomid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.entity.TopicEntity;

import java.util.UUID;

@Repository
public interface TopicRepo extends JpaRepository<TopicEntity, UUID> {
    @Modifying
    @Transactional
    @Query("update TopicEntity t set t.name = :name where t.id = :id")
    void updateById(@Param("id") UUID id, @Param("name") String name);
}
