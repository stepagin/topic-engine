package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.DTO.MessageDTO;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.entity.TopicEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.exception.ValidationException;
import ru.stepagin.atiomid.repository.MessageRepo;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public List<MessageDTO> getAllMessagesByTopicId(UUID topicId) {
        List<MessageEntity> messageEntities = messageRepo.findAllByTopic(topicId);
        return messageEntities.stream().map(MessageDTO::new).toList();
    }

    @Transactional
    public MessageEntity save(String message, PersonEntity person, TopicEntity topic) {
        if (message.isEmpty()) {
            throw new ValidationException("Message text too short");
        }
        // TODO: get person by Spring HTTP Basic auth
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText(message);
        messageEntity.setPerson(person);
        messageEntity.setTopic(topic);
        return messageRepo.save(messageEntity);
    }

    @Transactional
    public void updateMessage(MessageDTO message) {
        // TODO: check user has access
        MessageEntity messageEntity = this.getMessageEntityById(message.getId());
        messageRepo.updateTextById(messageEntity.getId(), message.getText());
    }

    public MessageEntity getMessageEntityById(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            throw new InvalidIdSuppliedException("Invalid message id");
        }
        MessageEntity messageEntity = messageRepo.findById(uuid).orElse(null);
        if (messageEntity == null) {
            throw new InvalidIdSuppliedException("Invalid message id");
        }
        return messageEntity;
    }

    public void deleteMessageById(String messageId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(messageId);
        } catch (Exception e) {
            throw new InvalidIdSuppliedException("Invalid message id");
        }
        messageRepo.deleteById(uuid);
    }
}
