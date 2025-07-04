package org.banco.exception.util;

import org.banco.model.ErrorResponse;

public class ErrorResponseBuilder {
    private final ErrorResponse response = new ErrorResponse();

    public ErrorResponseBuilder code(ErrorResponse.CodeEnum code) {
        response.setCode(code);
        return this;
    }

    public ErrorResponseBuilder message(String msg) {
        response.setMessage(msg);
        return this;
    }

    public ErrorResponse build() {
        return response;
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }
}
