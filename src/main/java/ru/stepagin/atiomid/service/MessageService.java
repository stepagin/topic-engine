package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepagin.atiomid.DTO.MessageDTO;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PostEntity;
import ru.stepagin.atiomid.exception.InvalidIdSuppliedException;
import ru.stepagin.atiomid.exception.ValidationException;
import ru.stepagin.atiomid.repository.MessageRepo;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private PostService postService;

    @Transactional
    public MessageEntity save(String message) {
        if (message.isEmpty()) {
            throw new ValidationException("Message text too short");
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText(message);
        messageEntity.setCreatedDate(OffsetDateTime.now(ZoneId.of("Europe/Moscow")));
        return messageRepo.save(messageEntity);
    }

    public List<MessageDTO> getAllMessagesByTopicId(UUID topicId) {
        List<PostEntity> postEntities = postService.getAllByTopicId(topicId);
        return postEntities.stream().map(MessageDTO::new).toList();
    }

    public void updateMessage(MessageDTO message) {
        MessageEntity messageEntity = this.getMessageEntityById(message.getId());
        messageRepo.updateTextById(messageEntity.getId(), message.getText());
        messageEntity.setText(message.getText());
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
}
