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
    @ManyToOne(optional = false)
    private TopicEntity topic;
    @ManyToOne(optional = false)
    private MessageEntity message;
    @ManyToOne(optional = false)
    private PersonEntity person;

}
