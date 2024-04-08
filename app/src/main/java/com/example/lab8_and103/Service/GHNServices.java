package com.example.lab8_and103.Service;

import com.example.lab8_and103.Model.District;
import com.example.lab8_and103.Model.DistrictRequest;
import com.example.lab8_and103.Model.GHNOrderRequest;
import com.example.lab8_and103.Model.GHNOrderResponse;
import com.example.lab8_and103.Model.Order;
import com.example.lab8_and103.Model.Province;
import com.example.lab8_and103.Model.ResponseGHN;
import com.example.lab8_and103.Model.Ward;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GHNServices {
    public static String GHN_URL = "https://dev-online-gateway.ghn.vn/";

    @GET("/shiip/public-api/master-data/province")
    Call<ResponseGHN<ArrayList<Province>>> getListProvince();

    @POST("/shiip/public-api/master-data/district")
    Call<ResponseGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("/shiip/public-api/master-data/ward")
    Call<ResponseGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);

    @POST("/shiip/public-api/v2/shipping-order/create")
    Call<ResponseGHN<GHNOrderResponse>> GHNOrder(@Body GHNOrderRequest ghnOrderRequest);
}
