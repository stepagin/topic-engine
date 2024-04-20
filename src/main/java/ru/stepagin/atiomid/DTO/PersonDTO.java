package ru.stepagin.atiomid.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepagin.atiomid.entity.PersonEntity;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String name;
    private OffsetDateTime creationDate;

    public PersonDTO(PersonEntity entity) {
        this.name = entity.getName();
        this.creationDate = entity.getCreatedDate();
    }
}
