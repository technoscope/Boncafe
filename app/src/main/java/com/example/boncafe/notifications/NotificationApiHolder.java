package com.example.boncafe.notifications;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationApiHolder {
    @GET("api/getnotifications?key=5&auth_key=D3phbZlLWDm0hRxe&limit=3")
    Call<List<NotificationModel>> getAds();
}
