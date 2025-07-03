package org.banco.service.impl;

import lombok.RequiredArgsConstructor;
import org.banco.model.ErrorResponse;
import org.banco.repository.OrderRepository;
import org.banco.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.banco.entity.Order;
import org.banco.entity.Product;
import org.banco.exception.*;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void updateProductQuantityInOrder(Long orderId, Long productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.INVALID_QUANTITY, "La cantidad no puede ser negativa."));
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.ORDER_NOT_FOUND, "Pedido no encontrado con ID: " + orderId)));

        Product product = order.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.PRODUCT_NOT_FOUND_IN_ORDER, "Producto no encontrado en el pedido.")));

        if (newQuantity > product.getAvailableQuantity()) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.EXCEEDED_QUANTITY, "Cantidad excede la disponible."));
        }

        product.setAvailableQuantity(newQuantity);
        orderRepository.save(order);
    }
}
