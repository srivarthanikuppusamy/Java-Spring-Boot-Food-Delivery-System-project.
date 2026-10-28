package com.fooddelivery.exception;

public class InvalidOrderQuantityException extends FoodDeliveryException {
    public InvalidOrderQuantityException(String message) { super(message); }
}