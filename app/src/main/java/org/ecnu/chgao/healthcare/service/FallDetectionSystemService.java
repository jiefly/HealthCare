package org.ecnu.chgao.healthcare.service;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chgao on 17-5-26.
 */

public interface FallDetectionSystemService {
    @FormUrlEncoded
    @POST("/FallDetectionSystem/capture/http_server")
    Call<String> login(@Field(value = "phone", encoded = true) String id, @Field(value = "passwordMd5", encoded = true) String pwd);
}
