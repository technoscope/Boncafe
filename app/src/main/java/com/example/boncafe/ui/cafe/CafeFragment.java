package com.example.boncafe.ui.cafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boncafe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CafeFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabs;
    public static View parentview;

    SelectionPagerAdabter sectionsPagerAdapter;
    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cafe, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        sectionsPagerAdapter = new SelectionPagerAdabter(getFragmentManager());
        sectionsPagerAdapter.addFragment(new MapsFragment(), "Maps");
        sectionsPagerAdapter.addFragment(new BranchesListFragment(), "Boncafe's");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        parentview=view;
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    String baseurl = "https://bon.com.sa/";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Boncafe Branches");
        myRef.keepSynced(true);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};

        if (!hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<BranchesModel>> listCall = jsonPlaceHolderApi.getbranches();
        listCall.enqueue(new Callback<List<BranchesModel>>() {
            @Override
            public void onResponse(Call<List<BranchesModel>> call, Response<List<BranchesModel>> response) {
                if (!response.isSuccessful()) {
                    // print console
                    Log.d("response", String.valueOf(+response.code()));
                    return;
                }
                try {
                    List<BranchesModel> models = response.body();
                    myRef.setValue(models);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<BranchesModel>> call, Throwable t) {
                Log.d("fail", "failed");
            }
        });
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}