package com.project.job.util;

import com.project.job.dto.ResponseObject;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),null,"Internal server error"
        );
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseObject> handleResourceNotFoundException(NoResourceFoundException e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),null,"No resources found"
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotFoundException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ResponseObject> handleNotFoundException(Exception e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),null, "Not found"
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseObject> handleBadRequestException(Exception e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),null,"Bad request"
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = String.format("Invalid parameter %s", e.getName());
        ResponseObject res = new ResponseObject(
                HttpStatus.BAD_REQUEST.value(),
                message,null,"Bad request"
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Object errorDetails;
        if (fieldErrors.size() == 1) {
            errorDetails = fieldErrors.get(0).getDefaultMessage();
        } else {
            errorDetails = fieldErrors.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }

        ResponseObject res = new ResponseObject(HttpStatus.BAD_REQUEST.value(), errorDetails, null, "Validation error");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request body",null,"Bad request"
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseObject> handleUnauthorizedException(UnauthorizedException e) {
        ResponseObject res = new ResponseObject(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),null,"Authentication error"
        );
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }
}

