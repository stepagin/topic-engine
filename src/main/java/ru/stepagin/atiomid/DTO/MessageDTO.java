package ru.stepagin.atiomid.DTO;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepagin.atiomid.entity.MessageEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String id;
    @Nonnull
    private String text;
    @Nonnull
    private String author;
    private String created;


    public MessageDTO(MessageEntity messageEntity) {
        this(
                messageEntity.getId().toString(),
                messageEntity.getText(),
                messageEntity.getPerson().getName(),
                messageEntity.getCreatedDate().toString()
        );
    }

}
