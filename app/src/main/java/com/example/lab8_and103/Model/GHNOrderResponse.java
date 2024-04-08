package com.example.lab8_and103.Model;

public class GHNOrderResponse {
    private String order_code;

    public GHNOrderResponse() {
    }

    public GHNOrderResponse(String order_code) {
        this.order_code = order_code;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }
}
