package com.example.theater.validations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
public class ValidationError extends BasicError {
    public ValidationError(List<FieldError> errors) {
        super("Validation error", errors, HttpStatus.BAD_REQUEST.value());
    }
}