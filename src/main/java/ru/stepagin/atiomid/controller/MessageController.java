package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.service.PostService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${app.api.prefix}/message")
public class MessageController {
    @Autowired
    private PostService postService;

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") String messageId) {
        postService.deleteMessageById(messageId);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("Message deleted successfully");
    }

}
