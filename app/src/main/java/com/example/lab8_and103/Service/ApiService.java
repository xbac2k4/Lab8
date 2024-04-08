package com.example.lab8_and103.Service;

import com.example.lab8_and103.Model.Order;
import com.example.lab8_and103.Model.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    String ipv4 = "192.168.1.118";
    public static String BASE_URL = "http://"+ ipv4 +":3000/api/";
    @POST("add-order")
    Call<Response<Order>> order(@Body Order order);
}
