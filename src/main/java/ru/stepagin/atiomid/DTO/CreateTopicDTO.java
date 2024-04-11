package ru.stepagin.atiomid.DTO;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTopicDTO {
    @Nonnull
    private String topicName;
    private MessageDTO message;
}
