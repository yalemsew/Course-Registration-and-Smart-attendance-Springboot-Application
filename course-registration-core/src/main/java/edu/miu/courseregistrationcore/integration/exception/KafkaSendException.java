package edu.miu.courseregistrationcore.integration.exception;

public class KafkaSendException extends RuntimeException{
    public KafkaSendException(String message) {
        super(message);
    }
}
