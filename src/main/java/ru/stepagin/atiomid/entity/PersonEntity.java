package ru.stepagin.atiomid.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.stepagin.atiomid.security.ApplicationSecurityConfig;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "person")
@Getter
@Setter
@RequiredArgsConstructor
public class PersonEntity {
    @Id
    @NotEmpty(message = "name is required")
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @NotEmpty(message = "password is required")
    @Column(name = "password", nullable = false)
    private String password;
    @CreationTimestamp
    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate = (OffsetDateTime.now(ZoneId.of("Europe/Moscow")));

    public PersonEntity(String name, String passwordNotEncoded) {
        this.name = name;
        this.password = ApplicationSecurityConfig.passwordEncoder().encode(passwordNotEncoded);
    }
}
