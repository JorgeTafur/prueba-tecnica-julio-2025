package org.banco.exception.handler;

import org.banco.exception.*;
import org.banco.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.banco.exception.util.ErrorResponseBuilder.builder;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                builder()
                        .code(ErrorResponse.CodeEnum.ORDER_NOT_FOUND)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ProductNotFoundExceptionInOrder.class)
    public ResponseEntity<ErrorResponse> handle(ProductNotFoundExceptionInOrder ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                builder()
                        .code(ErrorResponse.CodeEnum.PRODUCT_NOT_FOUND_IN_ORDER)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidQuantityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                builder()
                        .code(ErrorResponse.CodeEnum.INVALID_QUANTITY)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ExceededQuantityException.class)
    public ResponseEntity<ErrorResponse> handle(ExceededQuantityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                builder()
                        .code(ErrorResponse.CodeEnum.EXCEEDED_QUANTITY)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ErrorResponse> handle(UpdateFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                builder()
                        .code(ErrorResponse.CodeEnum.UPDATE_FAILED)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotCreateTransaction(CannotCreateTransactionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                builder()
                        .code(ErrorResponse.CodeEnum.UPDATE_FAILED)
                        .message("No se pudo conectar con la base de datos.")
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                builder()
                        .code(ErrorResponse.CodeEnum.UPDATE_FAILED)
                        .message("Error interno del servidor.")
                        .build()
        );
    }
}
