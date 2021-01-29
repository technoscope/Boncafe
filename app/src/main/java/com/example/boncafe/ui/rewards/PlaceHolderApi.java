package com.example.boncafe.ui.rewards;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

interface PlaceHolderApi {
    @GET("api/getdeal?key=5&auth_key=D3phbZlLWDm0hRxe")
//    Call<RewardApiModel> getbranches(@Field("key") String key, @Field("auth_key") String auth_key);
    Call<List<RewardApiModel>> getrewards();

}
