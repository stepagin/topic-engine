package ru.stepagin.atiomid.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${app.api.prefix}/message")
public class MessageController {
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") String messageId) {
        // TODO: delete
        throw new UnsupportedOperationException("Not supported yet.");
//        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("Message deleted successfully");
    }
}
