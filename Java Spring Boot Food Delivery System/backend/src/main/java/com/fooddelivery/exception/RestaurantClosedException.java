package com.fooddelivery.exception;

public class RestaurantClosedException extends FoodDeliveryException {
    public RestaurantClosedException(String message) { super(message); }
}