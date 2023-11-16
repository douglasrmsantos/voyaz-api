package io.github.dsjdevelopment.voyaz.api.infra.exception;

import io.github.dsjdevelopment.voyaz.api.domain.ExceptionValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity dealWith404Error() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity dealWith400Error(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity dealWith400Error(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity dealWithBadCredentialsError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity dealWithAuthenticationError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failure!");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity dealWithAcessDeniedError() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acess denied!");
    }

    @ExceptionHandler(ExceptionValidation.class)
    public ResponseEntity businessRuleError(ExceptionValidation ex) {
        return ResponseEntity.badRequest().body("Erro: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity dealWith500Error(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }

    private record ValidationErrorData(String field, String message) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
