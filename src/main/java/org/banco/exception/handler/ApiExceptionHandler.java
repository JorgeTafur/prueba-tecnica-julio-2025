package org.banco.exception.handler;

import org.banco.exception.ApiException;
import org.banco.model.ErrorResponse;
import org.banco.model.ErrorResponse.CodeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse err = ex.getErrorResponse();
        CodeEnum code = err.getCode();

        if (code == CodeEnum.ORDER_NOT_FOUND || code == CodeEnum.PRODUCT_NOT_FOUND_IN_ORDER) {
            return ResponseEntity.status(404).body(err);
        } else if (code == CodeEnum.INVALID_QUANTITY) {
            return ResponseEntity.status(400).body(err);
        } else if (code == CodeEnum.EXCEEDED_QUANTITY) {
            return ResponseEntity.status(409).body(err);
        } else if (code == CodeEnum.UPDATE_FAILED) {
            return ResponseEntity.status(500).body(err);
        }

        // Fallback por si se agrega un nuevo enum en el futuro
        return ResponseEntity.status(500).body(new ErrorResponse(CodeEnum.UPDATE_FAILED, "Error desconocido."));
    }
}
