package org.banco.service.impl;

import lombok.RequiredArgsConstructor;
import org.banco.entity.Order;
import org.banco.entity.OrderProduct;
import org.banco.exception.*;
import org.banco.repository.OrderProductRepository;
import org.banco.repository.OrderRepository;
import org.banco.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    public void updateProductQuantityInOrder(Long orderId, Long productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new InvalidQuantityException();
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId)
                .orElseThrow(ProductNotFoundExceptionInOrder::new);

        if (newQuantity > orderProduct.getProduct().getAvailableQuantity()) {
            throw new ExceededQuantityException();
        }

        try {
            orderProduct.setQuantity(newQuantity);
            orderProductRepository.save(orderProduct);
        } catch (Exception ex) {
            throw new UpdateFailedException(ex);
        }
    }
}
