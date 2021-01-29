package com.example.boncafe.ui.faqs;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface ApiPlaceHolder {
    @FormUrlEncoded
    @POST("getsprt")
    Call <List<FaqModel>> FAQ_MODEL_CALL(@Field("key") String key, @Field("auth_key") String auth_key);
}

