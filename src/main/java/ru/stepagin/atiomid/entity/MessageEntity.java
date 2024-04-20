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
@Table(name = "message")
@Getter
@Setter
@RequiredArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Lob
    @Column(name = "text", nullable = false)
    private String text;
    @CreationTimestamp
    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdDate = (OffsetDateTime.now(ZoneId.of("Europe/Moscow")));
    @ManyToOne(optional = false)
    private PersonEntity person;
    @ManyToOne(optional = false)
    private TopicEntity topic;

    public MessageEntity(String text, PersonEntity person, TopicEntity topic) {
        this.text = text;
        this.person = person;
        this.topic = topic;
    }
}
