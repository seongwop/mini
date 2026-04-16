package com.example.mini.order;

import com.example.mini.product.Product;
import com.example.mini.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + request.getProductId())
        );
        Order order = new Order(product);
        Order saved = orderRepository.save(order);
        return new OrderResponse(saved);
    }

    public OrderResponse getOrder (Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다. id=" + id));
        return new OrderResponse(order);
    }
}
