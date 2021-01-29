package com.example.boncafe.ui.home;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.boncafe.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridView;
    HomeModel model;
    ArrayList<HomeModel> homelist;
    HomeAdapter adapter;
    SimpleDraweeView mAdvertisment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.id_home_gridview);
        model = new HomeModel();
        model.setId(1);
        model.setText("Register yoursef for App-ealing treat!");
        model.setDrawablePath(R.drawable.one);
        homelist = new ArrayList<>();
        homelist.add(model);
        mAdvertisment = view.findViewById(R.id.id___ads);

        HomeModel model1 = new HomeModel();
        model1.setId(2);
        model1.setText("Redeem your super deal here");
        model1.setDrawablePath(R.drawable.two);
        homelist.add(model1);

        HomeModel model2 = new HomeModel();
        model2.setId(2);
        model2.setText("Drive thru to nearest BonCafe's");
        model2.setDrawablePath(R.drawable.three);
        homelist.add(model2);

        HomeModel model3 = new HomeModel();
        model3.setId(2);
        model3.setText("Promo code");
        model3.setDrawablePath(R.drawable.four);
        homelist.add(model3);

        HomeModel model4 = new HomeModel();
        model4.setId(2);
        model4.setText("Follow us on Instagram");
        model4.setDrawablePath(R.drawable.five);
        homelist.add(model4);

        HomeModel model5 = new HomeModel();
        model5.setId(2);
        model5.setText("Get in touch with us on Facebook");
        model5.setDrawablePath(R.drawable.six);
        homelist.add(model5);

        HomeModel model6 = new HomeModel();
        model6.setId(2);
        model6.setText("Twitter");
        model6.setDrawablePath(R.drawable.seven);
        homelist.add(model6);

        mCallAdvertisementApi();

        adapter = new HomeAdapter(getContext(), homelist);
        gridView.setAdapter(adapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        return root;
    }

    private void mCallAdvertisementApi() {
        String baseurl = "http://boncafe.botsolutions.org/public/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AdsApiPlaceHolder jsonPlaceHolderApi = retrofit.create(AdsApiPlaceHolder.class);
        Call<List<AdsModel>> listCall = jsonPlaceHolderApi.getAds();
        listCall.enqueue(new Callback<List<AdsModel>>() {
            @Override
            public void onResponse(Call<List<AdsModel>> call, Response<List<AdsModel>> response) {
                List<AdsModel> models = response.body();
                AdsModel model = new AdsModel();
//                model.setEnd(models.get(1).getEnd());
//                model.setStart(models.get(1).getStart());
//                model.setUrl(models.get(1).getUrl());
                Uri uri = Uri.parse(models.get(1).getUrl());
                mAdvertisment.setImageURI(uri);
            }
            @Override
            public void onFailure(Call<List<AdsModel>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}