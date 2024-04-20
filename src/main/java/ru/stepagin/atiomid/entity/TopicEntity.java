package ru.stepagin.atiomid.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "topic")
@Getter
@Setter
@RequiredArgsConstructor
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @CreationTimestamp
    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate = (OffsetDateTime.now(ZoneId.of("Europe/Moscow")));
    @ManyToOne(optional = false)
    private PersonEntity creator;

    public TopicEntity(String name, PersonEntity creator) {
        this.name = name;
        this.creator = creator;
    }
}
