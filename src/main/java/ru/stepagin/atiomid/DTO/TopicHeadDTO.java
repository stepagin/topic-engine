package ru.stepagin.atiomid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.stepagin.atiomid.entity.TopicEntity;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TopicHeadDTO {
    private String id;
    private String name;
    private String created;

    public TopicHeadDTO(TopicEntity entity) {
        this(entity.getId().toString(), entity.getName(), entity.getCreatedDate().toString());
    }
}
