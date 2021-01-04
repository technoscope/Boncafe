package com.example.boncafe.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.boncafe.R;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

class HomeAdapter extends BaseAdapter {
    private LayoutInflater inflator;
    private Context context;
    private ArrayList<HomeModel> viewList;
    CardView container;
    TextView text;


    public HomeAdapter(Context context, ArrayList<HomeModel> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflator.inflate(R.layout.home_view, null);
        container = view.findViewById(R.id.id_homeview_container);
        container.setBackground(context.getDrawable(viewList.get(i).getDrawablePath()));
        ImageView imageView=view.findViewById(R.id.id_img);
        //imageView.setImageDrawable(context.getDrawable(viewList.get(i).getDrawablePath()));
        text = view.findViewById(R.id.id_homeview_text);
        text.setText(viewList.get(i).getText());
        return view;
    }
}
