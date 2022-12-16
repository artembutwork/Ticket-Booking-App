package com.touk.app.exception;

import com.touk.app.helper.LocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler{

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @Autowired
    public ExceptionHandling(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(value = InvalidRequestDataException.class)
    public ResponseEntity<Object> handleCustomRuntimeException(InvalidRequestDataException ex, WebRequest request) {
        String responseBody = messageSource.getMessage(
                ex.getMessage(),
                null,
                localeResolver.resolveLocale(ex.getRequest()));
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
