package com.sm.sdk.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Method {
    @GET("/demo/e-carryout/api/gate/?UID=4cec00d0875d1294ebfbaf44d62b6041&token=dcc729c721b2eb21cb771e5747fc35d9")
    Call<Model> getAllData();

    String Base_url = "https://www.tkig.co.th";
}
