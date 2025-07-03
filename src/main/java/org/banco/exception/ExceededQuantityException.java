package org.banco.exception;

public class ExceededQuantityException extends RuntimeException {
    public ExceededQuantityException() {
        super("Cantidad excede la disponible del producto.");
    }
}
