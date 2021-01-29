package com.example.boncafe.ui.cafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boncafe.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.example.boncafe.ui.cafe.MyBranchesListRecyclerViewAdapter.sheetmodel;

public class BranchDetailFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        //by binding
        View view = inflater.inflate(R.layout.fragment_branch_detail, container, false);
        TextView d = view.findViewById(R.id.id_bname);
        Toast.makeText(getContext(), " s="+d, Toast.LENGTH_SHORT).show();
        d.setText(sheetmodel.get(0).getName_en() + " " + sheetmodel.get(0).getName_ar());
        return view;
    }

}