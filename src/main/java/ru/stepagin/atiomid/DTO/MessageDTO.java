package ru.stepagin.atiomid.DTO;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PostEntity;

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


    public MessageDTO(MessageEntity messageEntity, String author) {
        this(messageEntity.getId().toString(), messageEntity.getText(), author, messageEntity.getCreatedDate().toString());
    }

    public MessageDTO(PostEntity postEntity) {
        this(
                postEntity.getMessage().getId().toString(),
                postEntity.getMessage().getText(),
                postEntity.getPerson().getName(),
                postEntity.getMessage().getCreatedDate().toString()
        );

    }
}
