package com.example.boncafe.notifications;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;
import com.example.boncafe.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("WorkerHasAPublicModifier")
public class NotificationWorker extends Worker {
    public static Context context;
    static String CHANNEL_ID = "1";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {
        createNotificationChannel();
        mCallNotificationApi();
        return Result.success();
    }

    public static void mCallNotificationApi() {
        String baseurl = "http://boncafe.botsolutions.org/public/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NotificationApiHolder jsonPlaceHolderApi = retrofit.create(NotificationApiHolder.class);
        Call<List<NotificationModel>> listCall = jsonPlaceHolderApi.getAds();
        listCall.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                List<NotificationModel> models = response.body();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.marker)
                        .setContentTitle("BonCafe")
                        .setContentText(models.get(1).getNotifications_message())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(models.get(1).getNotifications_message()))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
