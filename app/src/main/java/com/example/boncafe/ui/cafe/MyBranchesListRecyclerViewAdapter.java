package com.example.boncafe.ui.cafe;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boncafe.MainActivity;
import com.example.boncafe.R;
import com.example.boncafe.ui.cafe.dummy.DummyContent.DummyItem;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class MyBranchesListRecyclerViewAdapter extends RecyclerView.Adapter<MyBranchesListRecyclerViewAdapter.ViewHolder> {

    ArrayList<BranchesModel> models;
    Context context;
    Activity activity;
    public static ArrayList<BranchesModel> sheetmodel;

    public MyBranchesListRecyclerViewAdapter(Activity activity, Context context, ArrayList<BranchesModel> models) {
        this.context = context;
        this.models = models;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(models.get(position).getName_en() + " " + models.get(position).getName_ar());
        holder.address.setText(models.get(position).getAddress());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetmodel = new ArrayList<>();
                sheetmodel.add(models.get(position));
                showBottomSheetDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView address;
        public DummyItem mItem;
        public LinearLayout container;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.id_bname);
            address = (TextView) view.findViewById(R.id.id_baddress);
            container = view.findViewById(R.id.id_fragment_item_container);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + address.getText() + "'";
        }
    }

    //bottomsheet view & wiring
    public void showBottomSheetDialog() {
        GPS gps = new GPS(context);

        View view = activity.getLayoutInflater().inflate(R.layout.fragment_branch_detail, null);
        TextView bname = view.findViewById(R.id.id_detail_branch_name);
        bname.setText(sheetmodel.get(0).getName_en() + " " + sheetmodel.get(0).getName_ar());

        TextView address = view.findViewById(R.id.id_detail_branch_address);
        address.setText(sheetmodel.get(0).getAddress());

        TextView bcodeno = view.findViewById(R.id.id_detail_branch_code_no);
        bcodeno.setText(sheetmodel.get(0).getNo());

        TextView distance = view.findViewById(R.id.id_distance);
//        calculate distance and divide it by 1000 toget distance in km
        float dinkm=distFrom(gps.getlocation().getLatitude(), gps.getlocation().getLongitude(), Double.parseDouble(String.valueOf(sheetmodel.get(0).getLat())), Double.parseDouble(String.valueOf(sheetmodel.get(0).getLon())))/1000;
        String disres= String.valueOf(Math.round(dinkm));
        distance.setText(disres+" km");

        Button getdirection = view.findViewById(R.id.getdirection);
        getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNavigation(new LatLng(sheetmodel.get(0).getLat(), sheetmodel.get(0).getLon()));
            }
        });
        TextView telno = view.findViewById(R.id.id_telno);
        telno.setText(sheetmodel.get(0).getTel());
        LinearLayout con = view.findViewById(R.id.id_contact);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for call #phone_no
                String mobileNumber = sheetmodel.get(0).getTel();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
                intent.setData(Uri.parse("tel: " + mobileNumber)); // Data with intent respective action on intent
                context.startActivity(intent);
            }
        });
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();
    }

    //get direction on google maps
    private void startNavigation(LatLng targetLocation) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + targetLocation.latitude + "," + targetLocation.longitude + "&mode=c");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        try {
            mapIntent.setPackage("com.google.android.apps.maps");
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mapIntent);
    }

    //Distance between two locations
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