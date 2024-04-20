package ru.stepagin.atiomid.service;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public List<MessageDTO> getAllMessagesByTopicId(UUID topicId, PageRequest pageRequest) {
        List<MessageEntity> messageEntities = messageRepo.findAllByTopicId(topicId, pageRequest);
        return messageEntities.stream().map(MessageDTO::new).toList();
    }

    @Transactional
    public MessageEntity save(@Nonnull String message, @Nonnull PersonEntity person, @Nonnull TopicEntity topic) {
        if (message.isEmpty()) {
            throw new ValidationException("Message text too short");
        }
        MessageEntity messageEntity = new MessageEntity(message, person, topic);
        return messageRepo.save(messageEntity);
    }

    @Transactional
    public MessageDTO updateMessage(MessageDTO message) {
        MessageEntity messageEntity = this.getMessageEntityById(message.getId());
        messageRepo.updateTextById(messageEntity.getId(), message.getText());
        messageEntity.setText(message.getText());
        return new MessageDTO(messageEntity);
    }

    public MessageEntity getMessageEntityById(String id) {
        MessageEntity messageEntity = messageRepo.findById(UUID.fromString(id)).orElse(null);
        if (messageEntity == null) {
            throw new InvalidIdSuppliedException("Invalid message id");
        }
        return messageEntity;
    }

    public void deleteMessageById(String messageId) {
        messageRepo.deleteById(UUID.fromString(messageId));
    }
}
