package com.example.mini.order;

import com.example.mini.product.Product;
import com.example.mini.product.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + request.getProductId())
        );
        Order order = new Order(product);
        Order saved = orderRepository.save(order);
        OrderResponse response = new OrderResponse(saved);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getOrderId())).body(response);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다. id=" + id));
        return ResponseEntity.ok(new OrderResponse(order));
    }
}
