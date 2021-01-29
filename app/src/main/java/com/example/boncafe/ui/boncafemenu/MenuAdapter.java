package com.example.boncafe.ui.boncafemenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.boncafe.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    ArrayList<MenuModel> models;
    Context context;
    ProgressDialog progressBar;

    public MenuAdapter(Context context, ArrayList<MenuModel> models1, ProgressDialog progressBar) {
        this.context = context;
        this.models = models1;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        progressBar.dismiss();
        holder.itemname.setText(models.get(position).getName_en() + " " + models.get(position).getName_ar());
        holder.itemprice.setText("SR. " + models.get(position).getPrice());
        if (models.get(position).isType() == true) {
            holder.status.setText("Deliverable");
        } else {
            holder.status.setText("Not Deliverable ");
        }
        String url = models.get(position).getImg();
        Uri uri = Uri.parse(url);
        holder.draweeView.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemprice, status;
        LinearLayout container;
        SimpleDraweeView draweeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.id_menu_item_name);
            itemprice = itemView.findViewById(R.id.id_menu_item_price);
            status = itemView.findViewById(R.id.id_menu_item_status);
            container = itemView.findViewById(R.id.id_menu_container);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.id_menu_img);

        }
    }
}
