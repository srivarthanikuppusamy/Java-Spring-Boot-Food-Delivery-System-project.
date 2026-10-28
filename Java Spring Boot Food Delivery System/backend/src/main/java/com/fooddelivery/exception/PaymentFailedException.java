package com.fooddelivery.exception;

public class PaymentFailedException extends FoodDeliveryException {
    public PaymentFailedException(String message) { super(message); }
}