package com.example.boncafe.ui.boncafemenu;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.boncafe.R;
import com.example.boncafe.ui.cafe.BranchesModel;
import com.example.boncafe.ui.cafe.GPS;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuCoffeeFragment extends Fragment {
    RecyclerView recyclerView;
    MenuModel model;
    ArrayList<MenuModel> mmodels;
    ArrayList<BranchesModel> bmodels;
    private ArrayList<Float> markerDistance;
    private ArrayList<BranchesModel> filtermodels;
    public ArrayList<LatLng> latlngs;
    MenuAdapter adapter;
    public static String branchcode;
    DatabaseReference reference;
    GPS location;
    Handler handler;
    private static final String TAG = MenuCoffeeFragment.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url;// = "http://bon.com.sa/api/items.php?q=";
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_coffee, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.id_menu_recycle_view);
        bmodels = new ArrayList<>();
        Fresco.initialize(getContext());
        progressDialog = new ProgressDialog(MenuCoffeeFragment.this.getContext());
        progressDialog.setTitle("Fetching Menu...");
        progressDialog.show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Boncafe Branches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    BranchesModel bmodel = data.getValue(BranchesModel.class);
                    //add model to arraylist
                    bmodels.add(bmodel);
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNearestcafe();
                    }
                }, 1000);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void mNearestcafe() {
        location = new GPS(getContext());
        try {
            double lat = location.getlocation().getLatitude();
            double lon = location.getlocation().getLongitude();
            markerDistance = new ArrayList<>();
            //calculated distance will be added to markerDistance Array_list
            for (int i = 0; i < bmodels.size(); i++) {
                LatLng point = new LatLng(bmodels.get(i).getLat(), bmodels.get(i).getLon());
                float d = distFrom(lat, lon, point.latitude, point.longitude);
                markerDistance.add(d);
            }
            if (markerDistance.size() != 0) {
                float distance = Collections.min(markerDistance);
                int index = markerDistance.indexOf(distance);
                filtermodels = new ArrayList<>();
                filtermodels.add(bmodels.get(index));
                branchcode = filtermodels.get(0).getNo();
                callBranchesApi();

            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Enable your Location", Toast.LENGTH_SHORT).show();
        }

    }

    public void callBranchesApi() {
        url = "http://bon.com.sa/api/items.php?q=" + branchcode;
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;
                mmodels = new ArrayList<>();
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsono = jsonArray.getJSONObject(i);
                        MenuModel menuModel = new MenuModel();
                        menuModel.setName_en(jsono.getString("name_en"));
                        menuModel.setName_ar(jsono.getString("name_ar"));
                        menuModel.setImg(jsono.getString("img"));
                        menuModel.setQty(jsono.getString("qty"));
                        menuModel.setNo(jsono.getString("no"));
                        menuModel.setPrice(jsono.getString("price"));
                        menuModel.setType(jsono.getBoolean("type"));
                        Log.d("logresult", menuModel.getName_en());
                        mmodels.add(menuModel);
                    }
                    adapter = new MenuAdapter(getContext(), mmodels, progressDialog);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        mRequestQueue.add(mStringRequest);

    }

    public float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        Float float1 = new Float(dist * meterConversion).floatValue();
        return float1;
    }
}