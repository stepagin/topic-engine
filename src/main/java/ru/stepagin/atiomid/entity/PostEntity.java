package ru.stepagin.atiomid.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "topic_id", columnDefinition = "UUID")
    private UUID topicId;
    @Column(name = "message_id", columnDefinition = "UUID")
    private UUID messageId;
    @Column(name = "person_id", columnDefinition = "UUID")
    private UUID personId;

}
