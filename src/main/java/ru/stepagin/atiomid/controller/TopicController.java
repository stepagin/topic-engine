package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.DTO.*;
import ru.stepagin.atiomid.service.TopicService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody CreateTopicDTO topic) {
        return ResponseEntity.ok(topicService.createTopic(topic));
    }

    @PutMapping
    public ResponseEntity<TopicDTO> updateTopic(@RequestBody UpdateTopicDTO topic) {
        return ResponseEntity.ok(topicService.updateTopic(topic));
    }

    @GetMapping
    public ResponseEntity<List<TopicHeadDTO>> getAllTopics(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(topicService.getAllTopics(PageRequest.of(page, size)));
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicDTO> getAllMesages(
            @PathVariable("topicId") String topicId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(topicService.getById(topicId));
    }

    @PostMapping("/{topicId}/message")
    public ResponseEntity<TopicDTO> createMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message
    ) {
        return ResponseEntity.ok(topicService.createMessage(topicId, message));
    }

    @PutMapping("/{topicId}/message")
    public ResponseEntity<TopicDTO> updateMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message
    ) {
        return ResponseEntity.ok(topicService.updateMessage(topicId, message));
    }


}
