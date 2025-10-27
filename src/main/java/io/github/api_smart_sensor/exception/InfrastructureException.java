package io.github.api_smart_sensor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InfrastructureException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public InfrastructureException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
