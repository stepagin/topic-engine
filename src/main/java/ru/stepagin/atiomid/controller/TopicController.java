package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.DTO.*;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.security.SecurityService;
import ru.stepagin.atiomid.service.TopicService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private SecurityService securityService;

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody CreateTopicDTO topic, Authentication auth) {
        PersonEntity person = securityService.getPerson(auth);
        return ResponseEntity.ok(topicService.createTopic(topic, person));
    }

    @PutMapping
    @PreAuthorize("@securityService.isTopicOwner(#topic.id, authentication)")
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
    public ResponseEntity<TopicDTO> getAllMessages(
            @PathVariable("topicId") String topicId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(topicService.getById(topicId, PageRequest.of(page, size)));
    }

    @PostMapping("/{topicId}/message")
    public ResponseEntity<TopicDTO> createMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message,
            Authentication auth
    ) {
        PersonEntity person = securityService.getPerson(auth);
        return ResponseEntity.ok(topicService.createMessage(topicId, message, person));
    }

    @PutMapping("/{topicId}/message")
    @PreAuthorize("@securityService.isMessageOwner(#message.id, authentication)")
    public ResponseEntity<TopicDTO> updateMessage(
            @PathVariable("topicId") String topicId,
            @RequestBody MessageDTO message
    ) {
        return ResponseEntity.ok(topicService.updateMessage(topicId, message));
    }


}
