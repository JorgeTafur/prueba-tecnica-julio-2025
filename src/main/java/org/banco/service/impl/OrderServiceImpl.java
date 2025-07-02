package org.banco.service.impl;

import org.banco.model.ErrorResponse;
import org.banco.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.banco.entity.Order;
import org.banco.entity.Product;
import org.banco.exception.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Override
    public void updateProductQuantityInOrder(Long orderId, Long productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.INVALID_QUANTITY, "La cantidad no puede ser negativa."));
        }

        Order order = findOrderById(orderId); // Simulado

        Optional<Product> productOpt = order.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();

        if (!productOpt.isPresent()) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.PRODUCT_NOT_FOUND_IN_ORDER, "Producto no encontrado en el pedido."));
        }

        Product product = productOpt.get();

        if (newQuantity > product.getAvailableQuantity()) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.EXCEEDED_QUANTITY, "Cantidad excede la disponible."));
        }

        try {
            product.setAvailableQuantity(newQuantity);
            // Simula persistencia
        } catch (Exception e) {
            throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.UPDATE_FAILED, "Error inesperado al actualizar la base de datos."));
        }
    }

    private Order findOrderById(Long id) {
        throw new ApiException(new ErrorResponse(ErrorResponse.CodeEnum.ORDER_NOT_FOUND, "Pedido no encontrado con ID: " + id));
    }
}
