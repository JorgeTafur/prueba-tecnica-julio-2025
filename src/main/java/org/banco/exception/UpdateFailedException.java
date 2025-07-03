package org.banco.exception;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(Throwable cause) {
        super("Error inesperado al actualizar el pedido.", cause);
    }
}
