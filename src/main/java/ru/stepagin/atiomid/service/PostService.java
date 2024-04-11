package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.entity.PostEntity;
import ru.stepagin.atiomid.entity.TopicEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.repository.PostRepo;
import ru.stepagin.atiomid.repository.TopicRepo;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    public PostRepo postRepo;
    @Autowired
    public TopicRepo topicRepo;

    @Transactional
    public void savePost(TopicEntity topicEntity, MessageEntity messageEntity, PersonEntity personEntity) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTopic(topicEntity);
        postEntity.setMessage(messageEntity);
        postEntity.setPerson(personEntity);
        postRepo.save(postEntity);
    }

    public List<PostEntity> getAllByTopicId(UUID topicId) {
        return postRepo.findAllByTopicId(topicId);
    }

    public List<PostEntity> getAllByTopicId(String topicId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(topicId);
        } catch (Exception e) {
            throw new InvalidIdSuppliedException("Invalid topic id");
        }
        return getAllByTopicId(uuid);
    }

    public void deleteMessageById(String messageId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(messageId);
        } catch (Exception e) {
            throw new InvalidIdSuppliedException("Invalid message id");
        }
        postRepo.deleteByMessageId(uuid);
    }
}
