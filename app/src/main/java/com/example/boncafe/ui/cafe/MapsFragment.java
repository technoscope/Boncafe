package com.example.boncafe.ui.cafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.boncafe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.boncafe.ui.cafe.CafeFragment.parentview;

public class MapsFragment extends Fragment {

    GPS location;
    public GoogleMap googleMap1;
    public ArrayList<LatLng> latlngs;
    ArrayList<Float> markerDistance;
    ArrayList<String> branchname;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<BranchesModel> models;
    BranchesModel model = new BranchesModel();
    Handler handler;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap1 = googleMap;
            location = new GPS(MapsFragment.this.getContext());
            try {
                double lat = location.getlocation().getLatitude();
                double lon = location.getlocation().getLongitude();
                LatLng currentlocation = new LatLng(lat, lon);
                googleMap.addMarker(new MarkerOptions().position(currentlocation).title("My Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
                googleMap.setBuildingsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lon))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //    googleMap.setMinZoomPreference(12.0f);
                //   googleMap.setMaxZoomPreference(21.0f);
            } catch (Exception l) {
                l.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        FloatingActionButton fab = view.findViewById(R.id.fab_currentlocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = new GPS(MapsFragment.this.getContext());
                double lat = location.getlocation().getLatitude();
                double lon = location.getlocation().getLongitude();
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lon))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap1.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
        latlngs = new ArrayList<>();
        branchname = new ArrayList<>();
        markerDistance = new ArrayList<>();
        models = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Boncafe Branches");
        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    BranchesModel model = data.getValue(BranchesModel.class);
                    //add model to arraylist
                    models.add(model);
                    latlngs.add(new LatLng(model.getLat(), model.getLon()));
                    branchname.add(model.getName_en() + " " + model.getName_ar());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addbranchesmarker();
            }
        };
        handler.postDelayed(runnable, 1000);
        FloatingActionButton cuurentlocationfab = view.findViewById(R.id.fab_currentlocation);
        cuurentlocationfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = new GPS(MapsFragment.this.getContext());
                double lat = location.getlocation().getLatitude();
                double lon = location.getlocation().getLongitude();
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lon))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap1.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
        ExtendedFloatingActionButton filterfab = view.findViewById(R.id.fab_filter);
        filterfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNearestcafe();


            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    private void addbranchesmarker() {
        MarkerOptions options = new MarkerOptions();
        for (int i = 0; i < branchname.size(); i++) {

            LatLng point = latlngs.get(i);
            String bloodpointname = branchname.get(i);
            options.position(point);
//             BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.markerbrown);

            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            options.title(bloodpointname);
            googleMap1.addMarker(options);
        }
    }

    private void mNearestcafe() {
        location = new GPS(MapsFragment.this.getContext());
        double lat = location.getlocation().getLatitude();
        double lon = location.getlocation().getLongitude();
        markerDistance = new ArrayList<>();
        //calculated distance will be added to markerDistance Arraylist
        for (int i = 0; i < latlngs.size(); i++) {
            LatLng point = latlngs.get(i);
            float d = distFrom(lat, lon, point.latitude, point.longitude);
            markerDistance.add(d);
        }
        if (markerDistance.size() != 0) {
            float distance = Collections.min(markerDistance);
            int index = markerDistance.indexOf(distance);
            startNavigation(new LatLng(latlngs.get(index).latitude, latlngs.get(index).longitude));
//            String place = branchname.get(index);
            googleMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlngs.get(index).latitude, latlngs.get(index).longitude), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latlngs.get(index).latitude, latlngs.get(index).longitude))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap1.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void startNavigation(LatLng targetLocation) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + targetLocation.latitude + "," + targetLocation.longitude + "&mode=c");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        try {
            mapIntent.setPackage("com.google.android.apps.maps");
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(mapIntent);
    }

}