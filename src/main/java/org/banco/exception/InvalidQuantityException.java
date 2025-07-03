package org.banco.exception;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("La cantidad no puede ser negativa.");
    }
}
