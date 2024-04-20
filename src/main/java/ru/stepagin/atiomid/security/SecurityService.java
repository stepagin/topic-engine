package ru.stepagin.atiomid.security;

import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.stepagin.atiomid.entity.MessageEntity;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.entity.TopicEntity;
import ru.stepagin.atiomid.service.MessageService;
import ru.stepagin.atiomid.service.PersonService;
import ru.stepagin.atiomid.service.TopicService;

import java.util.Objects;

@Service
public class SecurityService {
    final PersonService personService;
    final MessageSource messageSource;
    final TopicService topicService;
    private final MessageService messageService;

    public SecurityService(PersonService personService, MessageSource messageSource, TopicService topicService, MessageService messageService) {
        this.personService = personService;
        this.messageSource = messageSource;
        this.topicService = topicService;
        this.messageService = messageService;
    }

    public PersonEntity getPerson(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return personService.getPerson(user.getUsername());
    }

    public boolean isTopicOwner(String topicId, Authentication authentication) {
        TopicEntity topic = topicService.getTopicEntityById(topicId);
        PersonEntity person = getPerson(authentication);
        if (topic != null && person != null) {
            return (Objects.equals(topic.getCreator().getName(), person.getName()));
        }
        return false;
    }

    public boolean isMessageOwner(String messageId, Authentication authentication) {
        MessageEntity message = messageService.getMessageEntityById(messageId);
        PersonEntity person = getPerson(authentication);
        if (message != null && person != null) {
            return (Objects.equals(message.getPerson().getName(), person.getName()));
        }
        return false;
    }
}
