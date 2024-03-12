package edu.miu.courseregistrationcore.integration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseOfferingNotFoundException extends RuntimeException {
    public CourseOfferingNotFoundException(String message) {
        super(message);
    }
}
