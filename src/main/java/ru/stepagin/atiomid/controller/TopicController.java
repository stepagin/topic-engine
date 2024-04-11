package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.DTO.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${app.api.prefix}/topic")
public class TopicController {

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody CreateTopicDTO topic) {
        // TODO: create
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping
    public ResponseEntity<TopicDTO> updateTopic(@RequestBody UpdateTopicDTO topic) {
        // TODO: update
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping
    public ResponseEntity<List<TopicHeadDTO>> getAllTopics() {
        // TODO: get all
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDTO> getAllMesages(@PathVariable("topicId") String topicId) {
        // todo: get all msgs from topic
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/{topicId}/message")
    public ResponseEntity<MessageDTO> createMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message
    ) {
        // todo: create message
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/{topicId}/message")
    public ResponseEntity<MessageDTO> updateMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message
    ) {
        // todo: update message
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
