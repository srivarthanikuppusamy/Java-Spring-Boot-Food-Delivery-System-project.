package com.fooddelivery.service;

import com.fooddelivery.dto.OrderRequest;
import com.fooddelivery.dto.SystemStateResponse;
import com.fooddelivery.exception.*;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    private static final double MIN_ORDER_VALUE = 200.0;
    private static final List<String> SERVICABLE_PINCODES = Arrays.asList("560001", "560002", "560038");
    private static final String VALID_COUPON = "SAVE50";

    private boolean isRestaurantOpen = true;
    private double walletBalance = 1000.0;
    private final Map<String, Integer> menuStock = new ConcurrentHashMap<>();

    public OrderService() {
        menuStock.put("Paneer Butter Masala", 5);
        menuStock.put("Chicken Biryani", 3);
        menuStock.put("Garlic Naan Combo", 8);
    }

    public synchronized SystemStateResponse getSystemState() {
        return new SystemStateResponse(walletBalance, new HashMap<>(menuStock));
    }

    public synchronized Map<String, Object> processOrder(OrderRequest req) {
        // 1. Restaurant Operating Hours
        LocalTime now = LocalTime.now();
        if (!isRestaurantOpen || now.isBefore(LocalTime.of(9, 0)) || now.isAfter(LocalTime.of(23, 0))) {
            throw new RestaurantClosedException("Restaurant is closed. Operating hours: 09:00 AM to 11:00 PM.");
        }

        // 2. Address Validation
        if (req.getAddress() == null || req.getAddress().trim().length() < 10) {
            throw new InvalidAddressException("Invalid delivery address. Complete address required.");
        }

        // 3. Serviceable Area Check
        if (!SERVICABLE_PINCODES.contains(req.getPincode())) {
            throw new OutOfDeliveryAreaException("Delivery unavailable for Pincode: " + req.getPincode());
        }

        // 4. Quantity & Stock Check
        int currentStock = menuStock.getOrDefault(req.getItem(), 0);
        if (req.getQuantity() <= 0 || req.getQuantity() > currentStock) {
            throw new InvalidOrderQuantityException("Invalid quantity requested! Available stock: " + currentStock);
        }

        double subtotal = req.getPrice() * req.getQuantity();

        // 5. Minimum Order Value Check
        if (subtotal < MIN_ORDER_VALUE) {
            throw new MinimumOrderValueException("Minimum order subtotal must be at least ₹" + MIN_ORDER_VALUE + ". Current subtotal: ₹" + subtotal);
        }

        // 6. Coupon Check
        double discount = 0.0;
        if (req.getCoupon() != null && !req.getCoupon().trim().isEmpty()) {
            if (!req.getCoupon().trim().equalsIgnoreCase(VALID_COUPON)) {
                throw new InvalidCouponException("Invalid or expired coupon code: " + req.getCoupon());
            }
            discount = 50.0;
        }

        double finalTotal = subtotal - discount;

        // 7. Wallet Check
        if (walletBalance < finalTotal) {
            throw new InsufficientWalletBalanceException("Insufficient wallet balance. Required: ₹" + finalTotal + ", Available: ₹" + walletBalance);
        }

        // 8. Payment Check
        if (req.getPayment() < finalTotal) {
            throw new PaymentFailedException("Payment failed! Required payment amount: ₹" + finalTotal);
        }

        // Deduct balance and update stock
        walletBalance -= finalTotal;
        int remainingStock = currentStock - req.getQuantity();
        menuStock.put(req.getItem(), remainingStock);

        saveReceiptToFile(req.getItem(), req.getQuantity(), finalTotal, req.getAddress());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order Placed Successfully! Total Paid: ₹" + finalTotal);
        response.put("walletBalance", walletBalance);
        response.put("remainingStock", remainingStock);

        return response;
    }

    private void saveReceiptToFile(String item, int qty, double amount, String address) {
        try (FileWriter fw = new FileWriter("order_receipts.txt", true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println("--- ORDER RECEIPT ---");
            out.println("Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            out.println("Item: " + item + " x " + qty);
            out.println("Total Paid: ₹" + amount);
            out.println("Delivery Address: " + address);
            out.println("---------------------\n");
        } catch (IOException e) {
            System.err.println("Receipt log warning: " + e.getMessage());
        }
    }
}