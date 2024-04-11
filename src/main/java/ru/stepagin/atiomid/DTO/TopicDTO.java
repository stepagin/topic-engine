package ru.stepagin.atiomid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private String id;
    private String topicName;
    private String created;
    private List<MessageDTO> messages;
}
