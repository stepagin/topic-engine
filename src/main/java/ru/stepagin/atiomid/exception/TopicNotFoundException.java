package ru.stepagin.atiomid.exception;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(final String message) {
        super(message);
    }
}
