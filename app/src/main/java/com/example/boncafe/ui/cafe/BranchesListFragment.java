package com.example.boncafe.ui.cafe;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boncafe.R;
import com.example.boncafe.ui.cafe.dummy.DummyContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A fragment representing a list of Items.
 */
public class BranchesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private DatabaseReference myRef;
    private ArrayList<Float> markerDistance;
    private ArrayList<BranchesModel> models;
    private ArrayList<BranchesModel> filtermodels;
    public ArrayList<LatLng> latlngs;
    GPS location;
    View parentview;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BranchesListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BranchesListFragment newInstance(int columnCount) {
        BranchesListFragment fragment = new BranchesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        models = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("Boncafe Branches");
        myRef.keepSynced(true);
        latlngs = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    BranchesModel model = data.getValue(BranchesModel.class);
                    //add model to arraylist
                    models.add(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        parentview = view;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }

                    recyclerView.setAdapter(new MyBranchesListRecyclerViewAdapter(getActivity(), getContext(), models));
                }
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new MyBranchesListRecyclerViewAdapter(getActivity(), getContext(), models));


            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);

        ExtendedFloatingActionButton filterfablist = view.findViewById(R.id.fab_filter_list);
        filterfablist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNearestcafe();
            }
        });
        return view;
    }

    private void mNearestcafe() {
        location = new GPS(BranchesListFragment.this.getContext());
        double lat = location.getlocation().getLatitude();
        double lon = location.getlocation().getLongitude();
        markerDistance = new ArrayList<>();
        //calculated distance will be added to markerDistance Arraylist
        for (int i = 0; i < models.size(); i++) {
            LatLng point = new LatLng(models.get(i).getLat(), models.get(i).getLon());
            float d = distFrom(lat, lon, point.latitude, point.longitude);
            markerDistance.add(d);
        }
        if (markerDistance.size() != 0) {

            float distance = Collections.min(markerDistance);
            int index = markerDistance.indexOf(distance);
            filtermodels = new ArrayList<>();
            filtermodels.add(models.get(index));
            if (parentview instanceof RecyclerView) {
                Context context = parentview.getContext();
                RecyclerView recyclerView = (RecyclerView) parentview;
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                recyclerView.setAdapter(new MyBranchesListRecyclerViewAdapter(getActivity(), getContext(), filtermodels));
            }
            RecyclerView recyclerView = (RecyclerView) parentview.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MyBranchesListRecyclerViewAdapter(getActivity(), getContext(), filtermodels));
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}