package com.fooddelivery.exception;

public class InsufficientWalletBalanceException extends FoodDeliveryException {
    public InsufficientWalletBalanceException(String message) { super(message); }
}