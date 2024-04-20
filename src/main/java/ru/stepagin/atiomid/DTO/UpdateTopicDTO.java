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
public class UpdateTopicDTO {
    @Nonnull
    private String id; // topic id
    @Nonnull
    private String name; // new topic name
}
