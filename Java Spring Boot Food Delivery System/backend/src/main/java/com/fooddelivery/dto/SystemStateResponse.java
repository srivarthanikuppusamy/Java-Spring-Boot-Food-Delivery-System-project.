package com.fooddelivery.dto;

import java.util.Map;

public class SystemStateResponse {
    private double walletBalance;
    private Map<String, Integer> menuStock;

    public SystemStateResponse(double walletBalance, Map<String, Integer> menuStock) {
        this.walletBalance = walletBalance;
        this.menuStock = menuStock;
    }

    public double getWalletBalance() { return walletBalance; }
    public Map<String, Integer> getMenuStock() { return menuStock; }
}