package com.example.boncafe.ui.accountscreen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface PlaceHolderApi {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/signup?")
    Call<AccountModel> CreateUserWithField(@Field("key") String key, @Field("auth_key") String auth_key, @Field("name") String name
            , @Field("u_email") String u_email, @Field("password") String password, @Field("psw_repeat") String psw_repeat, @Field("phone") String phone
            , @Field("virtual_cash") String virtual_cash, @Field("physical_cash") String physical_cash,@Field("u_status") String u_status);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/login?")
    Call<loginModel> LoginUserWithField(@Field("key") String key, @Field("auth_key") String auth_key, @Field("u_email") String u_email,
                                              @Field("password") String password);
}

//, @Field("dob") String dob, @Field("dob") String auth_key, @Field("auth_key") String auth_key, @Field("auth_key") String auth_key

