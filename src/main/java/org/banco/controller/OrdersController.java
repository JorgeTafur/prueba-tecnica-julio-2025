package org.banco.controller;

import lombok.RequiredArgsConstructor;
import org.banco.api.OrdersApi;
import org.banco.model.UpdateQuantityRequest;
import org.banco.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> updateProductQuantityInOrder(Long orderId, Long productId, UpdateQuantityRequest body) {
        orderService.updateProductQuantityInOrder(orderId, productId, body.getNewQuantity());
        return ResponseEntity.noContent().build();
    }
}
