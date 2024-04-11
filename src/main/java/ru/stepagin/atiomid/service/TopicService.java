package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.DTO.*;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.entity.PostEntity;
import ru.stepagin.atiomid.entity.TopicEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.exception.TopicNotFoundException;
import ru.stepagin.atiomid.exception.ValidationException;
import ru.stepagin.atiomid.repository.TopicRepo;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class TopicService {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private PersonService personService;
    @Autowired
    private PostService postService;
    @Autowired
    private MessageService messageService;

    public List<TopicHeadDTO> getAllTopics(PageRequest pageRequest) {
        Page<TopicEntity> page = topicRepo.findAll(pageRequest);
        return page.getContent().stream().map(TopicHeadDTO::new).toList();
    }

    @Transactional
    public TopicDTO createTopic(CreateTopicDTO createTopicDTO) {
        PersonEntity personEntity = personService.getPerson(createTopicDTO.getMessage().getAuthor());
        if (personEntity == null) {
            throw new InvalidIdSuppliedException("Person not found");
        }
        if (createTopicDTO.getTopicName().length() < 2) {
            throw new ValidationException("Topic name too short");
        }
        if (createTopicDTO.getTopicName().length() > 256) {
            throw new ValidationException("Topic name too long");
        }

        MessageEntity messageEntity = messageService.save(createTopicDTO.getMessage().getText());

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName(createTopicDTO.getTopicName());
        topicEntity.setCreatedDate(OffsetDateTime.now(ZoneId.of("Europe/Moscow")));
        topicEntity = topicRepo.save(topicEntity);

        postService.savePost(topicEntity, messageEntity, personEntity);

        return new TopicDTO(topicEntity, List.of(new MessageDTO(messageEntity, personEntity.getName())));
    }

    @Transactional
    public TopicDTO updateTopic(UpdateTopicDTO topic) {
        TopicEntity topicEntity = this.getTopicEntityById(topic.getId());
        if (topic.getName().length() < 2) {
            throw new ValidationException("Topic name too short");
        }
        if (topic.getName().length() > 256) {
            throw new ValidationException("Topic name too long");
        }
        topicEntity.setName(topic.getName());
        topicRepo.updateById(topicEntity.getId(), topic.getName());

        return new TopicDTO(topicEntity, messageService.getAllMessagesByTopicId(topicEntity.getId()));
    }

    public TopicDTO getById(String topicId) {
        TopicEntity topicEntity = this.getTopicEntityById(topicId);
        List<PostEntity> postEntities = postService.getAllByTopicId(topicId);
        return new TopicDTO(topicEntity, postEntities.stream().map(MessageDTO::new).toList());
    }

    public TopicEntity getTopicEntityById(String topicId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(topicId);
        } catch (Exception e) {
            throw new InvalidIdSuppliedException("Invalid topic id");
        }
        TopicEntity topicEntity = topicRepo.findById(uuid).orElse(null);
        if (topicEntity == null) {
            throw new TopicNotFoundException("Topic not found");
        }
        return topicEntity;
    }

    @Transactional
    public TopicDTO createMessage(String topicId, MessageDTO message) {
        MessageEntity messageEntity = messageService.save(message.getText());
        PersonEntity personEntity = personService.getPerson(message.getAuthor());
        TopicEntity topicEntity = this.getTopicEntityById(topicId);
        postService.savePost(topicEntity, messageEntity, personEntity);
        return this.getById(topicId);
    }

    @Transactional
    public TopicDTO updateMessage(String topicId, MessageDTO message) {
        if (message.getId() == null) {
            throw new InvalidIdSuppliedException("Message id is null");
        }
        messageService.updateMessage(message);
        return this.getById(topicId);
    }
}
