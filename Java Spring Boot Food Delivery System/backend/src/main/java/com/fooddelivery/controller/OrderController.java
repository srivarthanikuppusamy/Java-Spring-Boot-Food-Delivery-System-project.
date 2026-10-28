package com.fooddelivery.controller;

import com.fooddelivery.dto.OrderRequest;
import com.fooddelivery.dto.SystemStateResponse;
import com.fooddelivery.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows JS Frontend connection
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/system/state")
    public ResponseEntity<SystemStateResponse> getSystemState() {
        return ResponseEntity.ok(orderService.getSystemState());
    }

    @PostMapping("/orders/checkout")
    public ResponseEntity<Map<String, Object>> checkout(@RequestBody OrderRequest request) {
        Map<String, Object> result = orderService.processOrder(request);
        return ResponseEntity.ok(result);
    }
} 