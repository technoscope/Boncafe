package com.example.boncafe.ui.home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface AdsApiPlaceHolder {
    @GET("api/getadv?key=5&auth_key=D3phbZlLWDm0hRxe")
    Call<List<AdsModel>> getAds();
}
