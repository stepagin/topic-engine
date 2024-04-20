package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stepagin.atiomid.service.MessageService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${api.endpoint.base-url}/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @DeleteMapping("/{messageId}")
    @PreAuthorize("@securityService.isMessageOwner(#messageId, authentication)")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") String messageId) {
        messageService.deleteMessageById(messageId);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("Message deleted successfully");
    }
}
