package org.banco.exception;

public class ProductNotFoundExceptionInOrder extends RuntimeException {
    public ProductNotFoundExceptionInOrder() {
        super("Producto no encontrado en el pedido.");
    }
}
