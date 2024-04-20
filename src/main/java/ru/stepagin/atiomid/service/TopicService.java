package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.DTO.*;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.entity.TopicEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.exception.TopicNotFoundException;
import ru.stepagin.atiomid.exception.ValidationException;
import ru.stepagin.atiomid.repository.TopicRepo;

import java.util.List;
import java.util.UUID;

@Service
public class TopicService {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private MessageService messageService;

    public List<TopicHeadDTO> getAllTopics(PageRequest pageRequest) {
        Page<TopicEntity> page = topicRepo.findAll(pageRequest);
        return page.getContent().stream().map(TopicHeadDTO::new).toList();
    }

    @Transactional
    public TopicDTO createTopic(CreateTopicDTO createTopicDTO, PersonEntity creator) {
        if (creator == null) {
            throw new InvalidIdSuppliedException("Person not found");
        }
        if (createTopicDTO.getTopicName().length() < 2) {
            throw new ValidationException("Topic name is too short");
        }
        if (createTopicDTO.getTopicName().length() > 256) {
            throw new ValidationException("Topic name is too long");
        }

        TopicEntity topicEntity = new TopicEntity(createTopicDTO.getTopicName(), creator);
        topicEntity = topicRepo.save(topicEntity);

        MessageEntity messageEntity = messageService.save(
                createTopicDTO.getMessage().getText(),
                creator,
                topicEntity
        );

        return new TopicDTO(topicEntity, List.of(new MessageDTO(messageEntity)));
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
        List<MessageDTO> messages = messageService.getAllMessagesByTopicId(topicEntity.getId(), PageRequest.of(0, 20));
        return new TopicDTO(topicEntity, messages);
    }

    public TopicDTO getById(String topicId) {
        return this.getById(topicId, PageRequest.of(0, 20));
    }

    public TopicDTO getById(String topicId, PageRequest pageRequest) {
        TopicEntity topicEntity = this.getTopicEntityById(topicId);
        List<MessageDTO> messages = messageService.getAllMessagesByTopicId(UUID.fromString(topicId), pageRequest);
        return new TopicDTO(topicEntity, messages);
    }

    public TopicEntity getTopicEntityById(String topicId) {
        TopicEntity topicEntity = topicRepo.findById(UUID.fromString(topicId)).orElse(null);
        if (topicEntity == null) {
            throw new TopicNotFoundException("Topic not found");
        }
        return topicEntity;
    }

    @Transactional
    public TopicDTO createMessage(String topicId, MessageDTO message, PersonEntity person) {
        TopicEntity topic = this.getTopicEntityById(topicId);
        messageService.save(message.getText(), person, topic);
        return this.getById(topicId);
    }

    @Transactional
    public TopicDTO updateMessage(String topicId, MessageDTO message) {
        if (message.getId() == null) {
            throw new InvalidIdSuppliedException("Message id is null");
        }
        TopicEntity topic = getTopicEntityById(topicId);
        if (topic == null) {
            throw new InvalidIdSuppliedException("Topic not found");
        }
        MessageDTO newMessage = messageService.updateMessage(message);
        return new TopicDTO(topic, List.of(newMessage));
    }

}
