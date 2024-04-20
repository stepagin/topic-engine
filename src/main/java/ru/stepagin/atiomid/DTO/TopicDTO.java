package ru.stepagin.atiomid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepagin.atiomid.entity.TopicEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private String id;
    private String creator;
    private String topicName;
    private String created;
    private List<MessageDTO> messages;

    public TopicDTO(TopicEntity topicEntity, List<MessageDTO> messages) {
        this(
                topicEntity.getId().toString(),
                topicEntity.getCreator().getName(),
                topicEntity.getName(),
                topicEntity.getCreatedDate().toString(),
                messages
        );
    }
}
