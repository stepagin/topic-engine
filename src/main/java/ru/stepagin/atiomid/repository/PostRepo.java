package ru.stepagin.atiomid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepagin.atiomid.entity.PostEntity;

import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, UUID> {
}
