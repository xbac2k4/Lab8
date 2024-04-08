package com.example.lab8_and103.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab8_and103.Adapter.Adapter_Item_District_Select_GHN;
import com.example.lab8_and103.Adapter.Adapter_Item_Province_Select_GHN;
import com.example.lab8_and103.Adapter.Adapter_Item_Ward_Select_GHN;
import com.example.lab8_and103.Model.District;
import com.example.lab8_and103.Model.DistrictRequest;
import com.example.lab8_and103.Model.GHNItem;
import com.example.lab8_and103.Model.GHNOrderRequest;
import com.example.lab8_and103.Model.GHNOrderResponse;
import com.example.lab8_and103.Model.Order;
import com.example.lab8_and103.Model.Product;
import com.example.lab8_and103.Model.Province;
import com.example.lab8_and103.Model.ResponseGHN;
import com.example.lab8_and103.Model.Ward;
import com.example.lab8_and103.R;
import com.example.lab8_and103.Service.GHNRequest;
import com.example.lab8_and103.Service.GHNServices;
import com.example.lab8_and103.Service.HttpRequest;
import com.example.lab8_and103.databinding.ActivityLocationBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {
    private ActivityLocationBinding binding;
    private GHNRequest request;
    private GHNServices ghnServices;
    private String productId, productTypeId, productName, description, WardCode;
    private double rate, price;
    private int image, DistrictID, ProvinceID ;
    private Adapter_Item_Province_Select_GHN adapter_item_province_select_ghn;
    private Adapter_Item_District_Select_GHN adapter_item_district_select_ghn;
    private Adapter_Item_Ward_Select_GHN adapter_item_ward_select_ghn;
    TextInputEditText edt_name, edt_phone, edt_location;
    Button btn_order;
    GHNRequest ghnRequest;
    HttpRequest httpRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anhxa();
        request = new GHNRequest();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
            productTypeId = bundle.getString("productTypeId");
            productName = bundle.getString("productName");
            description = bundle.getString("description");
            rate = bundle.getDouble("rate");
            price = bundle.getDouble("price");
//            image = bundle.getInt("image");
        }

        request.callAPI().getListProvince().enqueue(responseProvince);
        binding.spProvince.setOnItemSelectedListener(onItemSelectedListener);
        binding.spDistrict.setOnItemSelectedListener(onItemSelectedListener);
        binding.spWard.setOnItemSelectedListener(onItemSelectedListener);

        binding.spProvince.setSelection(1);
        binding.spDistrict.setSelection(1);
        binding.spWard.setSelection(1);

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_name.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String location = edt_location.getText().toString().trim();

                if (WardCode.equals("")) {
                    Toast.makeText(LocationActivity.this, "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty() || phone.isEmpty() || location.isEmpty()) {
                    Toast.makeText(LocationActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Product product = (Product) getIntent().getExtras().getSerializable("item");
//                String productName = bundle.getString("productName");
//                Double price = bundle.getDouble("price");
//                String productID = bundle.getString("productId");

                GHNItem ghnItem = new GHNItem();
                ghnItem.setName(productName);
                ghnItem.setPrice(price);
                ghnItem.setCode(productId);
                ghnItem.setQuantity(1);
                ghnItem.setWeight(50);
                ArrayList<GHNItem> items = new ArrayList<>();
                items.add(ghnItem);
                GHNOrderRequest ghnOrderRequest = new GHNOrderRequest(
                        name,
                        phone,
                        location,
                        WardCode,
                        DistrictID,
                        items
                );
                ghnRequest.callAPI().GHNOrder(ghnOrderRequest).enqueue(responseOrder);
            }
        });

    }
    Callback<com.example.lab8_and103.Model.Response<Order>> responseOrderDatabase  = new Callback<com.example.lab8_and103.Model.Response<Order>>() {
        @Override
        public void onResponse(Call<com.example.lab8_and103.Model.Response<Order>> call, Response<com.example.lab8_and103.Model.Response<Order>> response) {
            if (response.isSuccessful()) {
                Toast.makeText(LocationActivity.this, "Cảm ơn bạn đã mua hàng", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(LocationActivity.this, "Lỗi 2", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<com.example.lab8_and103.Model.Response<Order>> call, Throwable t) {
            Log.e("Error", t.getMessage());
        }
    };
    Callback<ResponseGHN<GHNOrderResponse>> responseOrder = new Callback<ResponseGHN<GHNOrderResponse>>() {
        @Override
        public void onResponse(Call<ResponseGHN<GHNOrderResponse>> call, Response<ResponseGHN<GHNOrderResponse>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    Toast.makeText(LocationActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    Order order = new Order();
                    order.setOrder_code(response.body().getData().getOrder_code());
                    order.setId_user(getSharedPreferences("INFO", MODE_PRIVATE).getString("id", ""));
                    httpRequest.callAPI().order(order).enqueue(responseOrderDatabase);
                } else {
                    Toast.makeText(LocationActivity.this, "Lỗi 1", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LocationActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<GHNOrderResponse>> call, Throwable t) {
            Log.e("Error", t.getMessage());
        }
    };
    private void anhxa() {
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_location = findViewById(R.id.edt_location);
        btn_order = findViewById(R.id.btn_order);

        ghnRequest = new GHNRequest();
        httpRequest = new HttpRequest();
    }
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.sp_province) {
                ProvinceID = ((Province) parent.getAdapter().getItem(position)).getProvinceID();
                DistrictRequest districtRequest = new DistrictRequest(ProvinceID);
                request.callAPI().getListDistrict(districtRequest).enqueue(responseDistrict);
            } else if (parent.getId() == R.id.sp_district) {
                DistrictID = ((District) parent.getAdapter().getItem(position)).getDistrictID();
                request.callAPI().getListWard(DistrictID).enqueue(responseWard);
            } else if (parent.getId() == R.id.sp_ward) {
                WardCode = ((Ward) parent.getAdapter().getItem(position)).getWardCode();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    Callback<ResponseGHN<ArrayList<Province>>> responseProvince = new Callback<ResponseGHN<ArrayList<Province>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Province>>> call, Response<ResponseGHN<ArrayList<Province>>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){
                    ArrayList<Province> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinProvince(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Province>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lấy dữ liệu bị lỗi", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ResponseGHN<ArrayList<District>>> responseDistrict = new Callback<ResponseGHN<ArrayList<District>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<District>>> call, Response<ResponseGHN<ArrayList<District>>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){
                    ArrayList<District> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinDistrict(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<District>>> call, Throwable t) {

        }
    };

    Callback<ResponseGHN<ArrayList<Ward>>> responseWard = new Callback<ResponseGHN<ArrayList<Ward>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Ward>>> call, Response<ResponseGHN<ArrayList<Ward>>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){

                    if(response.body().getData() == null)
                        return;

                    ArrayList<Ward> ds = new ArrayList<>(response.body().getData());

                    ds.addAll(response.body().getData());
                    SetDataSpinWard(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Ward>>> call, Throwable t) {
            Toast.makeText(LocationActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    };

    private void SetDataSpinProvince(ArrayList<Province> ds){
        adapter_item_province_select_ghn = new Adapter_Item_Province_Select_GHN(this, ds);
        binding.spProvince.setAdapter(adapter_item_province_select_ghn);
    }

    private void SetDataSpinDistrict(ArrayList<District> ds){
        adapter_item_district_select_ghn = new Adapter_Item_District_Select_GHN(this, ds);
        binding.spDistrict.setAdapter(adapter_item_district_select_ghn);
    }

    private void SetDataSpinWard(ArrayList<Ward> ds){
        adapter_item_ward_select_ghn = new Adapter_Item_Ward_Select_GHN(this, ds);
        binding.spWard.setAdapter(adapter_item_ward_select_ghn );
    }

}