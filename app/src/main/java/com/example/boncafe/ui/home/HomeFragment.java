package com.example.boncafe.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.boncafe.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridView;
    HomeModel model;
    ArrayList<HomeModel> homelist;
    HomeAdapter adapter;
    ImageView image;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView=view.findViewById(R.id.id_home_gridview);
        image=view.findViewById(R.id.id_home_image);
        image.setImageDrawable(getContext().getDrawable(R.drawable.iconmainadd));
        model=new HomeModel();
        model.setId(1);
        model.setText("Register yoursef for App-ealing treat!");
        model.setDrawablePath(R.drawable.one);
        homelist=new ArrayList<>();
        homelist.add(model);

        HomeModel model1=new HomeModel();
        model1.setId(2);
        model1.setText("Redeem your Jazz super deal here");
        model1.setDrawablePath(R.drawable.two);
        homelist.add(model1);

        HomeModel model2=new HomeModel();
        model2.setId(2);
        model2.setText("Drive thru to nearest McDonald's");
        model2.setDrawablePath(R.drawable.three);
        homelist.add(model2);

        HomeModel model3=new HomeModel();
        model3.setId(2);
        model3.setText("Redeem your xyz deal");
        model3.setDrawablePath(R.drawable.four);
        homelist.add(model3);

        HomeModel model4=new HomeModel();
        model4.setId(2);
        model4.setText("super deal is here");
        model4.setDrawablePath(R.drawable.five);
        homelist.add(model4);

        HomeModel model5=new HomeModel();
        model5.setId(2);
        model5.setText("5 deal");
        model5.setDrawablePath(R.drawable.six);
        homelist.add(model5);

        HomeModel model6=new HomeModel();
        model6.setId(2);
        model6.setText("Seven deal");
        model6.setDrawablePath(R.drawable.seven);
        homelist.add(model6);

        adapter=new HomeAdapter(getContext(),homelist);
        gridView.setAdapter(adapter);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//              //  textView.setText(s);
//            }
//        });



        return root;
    }

}