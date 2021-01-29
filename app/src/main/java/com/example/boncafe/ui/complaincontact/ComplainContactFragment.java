package com.example.boncafe.ui.complaincontact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.boncafe.R;
import com.example.boncafe.ui.cafe.BranchesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ComplainContactFragment extends Fragment {
    Spinner spinner;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    DatabaseReference myRef;
    ArrayList<BranchesModel> models;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_complain_contact, container, false);
        list=new ArrayList<>();
        models=new ArrayList<>();
        spinner=view.findViewById(R.id.id_spinner_cafe_branches);
        myRef= FirebaseDatabase.getInstance().getReference("Boncafe Branches");
        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    BranchesModel model = data.getValue(BranchesModel.class);
                    list.add(model.getName_en()+" "+model.getName_ar());
                }
                adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        return view;
    }

}