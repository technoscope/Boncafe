package com.example.boncafe.ui.rewards;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.boncafe.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;

class RewardsAdabter extends BaseAdapter {
    Context context;
    ArrayList<RewardApiModel> models;
    LayoutInflater inflater;
    SimpleDraweeView container;
    TextView textView;

    public RewardsAdabter(Context context, ArrayList<RewardApiModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.rewards_view, null);
        container = view.findViewById(R.id.id_rewardview_img);
        textView = view.findViewById(R.id.id_rewardsview_text);
        try {
            String url = models.get(i).getUrl();
            Uri uri = Uri.parse(url);
            container.setImageURI(uri);
        } catch (Exception e) {
        }
        //textView.setText(models.get(i).getText());
        return view;
    }
}
