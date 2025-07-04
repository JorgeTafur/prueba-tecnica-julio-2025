package org.banco.service.impl;

import org.banco.entity.Order;
import org.banco.entity.OrderProduct;
import org.banco.entity.Product;
import org.banco.exception.*;
import org.banco.repository.OrderProductRepository;
import org.banco.repository.OrderRepository;
import org.banco.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderProduct orderProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new Order();

        Product product = new Product();
        product.setAvailableQuantity(10);

        orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(2);
    }

    @Test
    void shouldUpdateQuantitySuccessfully() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderProductRepository.findByOrderIdAndProductId(1L, 2L)).thenReturn(Optional.of(orderProduct));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderProductRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.updateProductQuantityInOrder(1L, 2L, 5);

        verify(productRepository).save(orderProduct.getProduct());
        verify(orderProductRepository).save(orderProduct);
        assertEquals(5, orderProduct.getQuantity());
        assertEquals(7, orderProduct.getProduct().getAvailableQuantity());
    }

    @Test
    void shouldThrowInvalidQuantityException_whenQuantityNegative() {
        assertThrows(InvalidQuantityException.class, () ->
                orderService.updateProductQuantityInOrder(1L, 2L, -1)
        );
    }

    @Test
    void shouldThrowOrderNotFoundException_whenOrderMissing() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                orderService.updateProductQuantityInOrder(1L, 2L, 3)
        );
    }

    @Test
    void shouldThrowProductNotFoundExceptionInOrder_whenProductMissing() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderProductRepository.findByOrderIdAndProductId(1L, 2L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptionInOrder.class, () ->
                orderService.updateProductQuantityInOrder(1L, 2L, 3)
        );
    }

    @Test
    void shouldThrowExceededQuantityException_whenQuantityExceedsAvailable() {
        orderProduct.getProduct().setAvailableQuantity(3);
        orderProduct.setQuantity(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderProductRepository.findByOrderIdAndProductId(1L, 2L)).thenReturn(Optional.of(orderProduct));

        assertThrows(ExceededQuantityException.class, () ->
                orderService.updateProductQuantityInOrder(1L, 2L, 5)
        );
    }

    @Test
    void shouldThrowUpdateFailedException_whenRepositorySaveFails() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderProductRepository.findByOrderIdAndProductId(1L, 2L)).thenReturn(Optional.of(orderProduct));
        doThrow(new RuntimeException("DB error")).when(orderProductRepository).save(any());

        assertThrows(UpdateFailedException.class, () ->
                orderService.updateProductQuantityInOrder(1L, 2L, 3)
        );
    }
}
