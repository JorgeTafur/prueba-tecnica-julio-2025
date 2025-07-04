package org.banco.service.impl;

import lombok.RequiredArgsConstructor;
import org.banco.entity.Order;
import org.banco.entity.OrderProduct;
import org.banco.entity.Product;
import org.banco.exception.*;
import org.banco.repository.OrderProductRepository;
import org.banco.repository.OrderRepository;
import org.banco.repository.ProductRepository;
import org.banco.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public void updateProductQuantityInOrder(Long orderId, Long productId, int newQuantity) {
        validateQuantity(newQuantity);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId)
                .orElseThrow(ProductNotFoundExceptionInOrder::new);

        int oldQuantity = orderProduct.getQuantity();

        validateAvailability(newQuantity, orderProduct, oldQuantity);

        updateQuantityAndStock(orderProduct, newQuantity, oldQuantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0)  {
            throw new InvalidQuantityException();
        }
    }

    private void validateAvailability(int newQuantity, OrderProduct orderProduct, int oldQuantity) {
        int difference = newQuantity - oldQuantity;
        int availableStock = orderProduct.getProduct().getAvailableQuantity();
        if (difference > availableStock) {
            throw new ExceededQuantityException();
        }
    }

    private void updateQuantityAndStock(OrderProduct orderProduct, int newQuantity, int oldQuantity) {
        try {
            int difference = newQuantity - oldQuantity;
            Product product = orderProduct.getProduct();
            product.setAvailableQuantity(product.getAvailableQuantity() - difference);

            productRepository.save(product);

            orderProduct.setQuantity(newQuantity);
            orderProductRepository.save(orderProduct);
        } catch (Exception ex) {
            throw new UpdateFailedException(ex);
        }
    }
}
