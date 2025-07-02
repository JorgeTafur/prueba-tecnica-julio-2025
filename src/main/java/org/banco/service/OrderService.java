package org.banco.service;

public interface OrderService {
    void updateProductQuantityInOrder(Long orderId, Long productId, int newQuantity);
}
