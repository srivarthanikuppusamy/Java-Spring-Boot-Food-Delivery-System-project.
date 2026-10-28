package com.fooddelivery.exception;

public class OutOfDeliveryAreaException extends FoodDeliveryException {
    public OutOfDeliveryAreaException(String message) { super(message); }
}