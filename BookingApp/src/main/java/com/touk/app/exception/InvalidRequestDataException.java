package com.touk.app.exception;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class InvalidRequestDataException extends RuntimeException {

    private final HttpServletRequest request;

    public InvalidRequestDataException(String message, HttpServletRequest request) {
        super(message);
        this.request = request;
    }
}
