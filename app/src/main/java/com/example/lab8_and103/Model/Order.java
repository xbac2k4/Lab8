package com.example.lab8_and103.Model;

public class Order {
    private String _id;
    private String order_code;
    private String id_user;

    public Order() {
    }

    public Order(String _id, String order_code, String id_user) {
        this._id = _id;
        this.order_code = order_code;
        this.id_user = id_user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrder_code(String orderCode) {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
