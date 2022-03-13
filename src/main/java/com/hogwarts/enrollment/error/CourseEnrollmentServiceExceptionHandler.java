package com.hogwarts.enrollment.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CourseEnrollmentServiceExceptionHandler  extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleInvalidInput(RuntimeException ex, WebRequest request) {
        log.error("Error ocurred during request processing: {}", ex);
        return handleExceptionInternal(ex, getErrorMessageMap(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        log.error("Error ocurred during request processing: {}", ex);
        return handleExceptionInternal(ex, getErrorMessageMap(ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private Map<String,String> getErrorMessageMap(String message) {
        Map<String,String> error = new HashMap<>();
        error.put("errorMessage", message);
        return error;
    }
}
