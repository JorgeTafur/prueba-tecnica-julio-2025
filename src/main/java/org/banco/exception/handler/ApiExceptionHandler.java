package org.banco.exception.handler;

import org.banco.exception.*;
import org.banco.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(OrderNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ErrorResponse.CodeEnum.ORDER_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundExceptionInOrder.class)
    public ResponseEntity<ErrorResponse> handle(ProductNotFoundExceptionInOrder ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ErrorResponse.CodeEnum.PRODUCT_NOT_FOUND_IN_ORDER, ex.getMessage()));
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidQuantityException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse(ErrorResponse.CodeEnum.INVALID_QUANTITY, ex.getMessage()));
    }

    @ExceptionHandler(ExceededQuantityException.class)
    public ResponseEntity<ErrorResponse> handle(ExceededQuantityException ex) {
        return ResponseEntity.status(409).body(new ErrorResponse(ErrorResponse.CodeEnum.EXCEEDED_QUANTITY, ex.getMessage()));
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ErrorResponse> handle(UpdateFailedException ex) {
        return ResponseEntity.status(500).body(new ErrorResponse(ErrorResponse.CodeEnum.UPDATE_FAILED, ex.getMessage()));
    }
}
