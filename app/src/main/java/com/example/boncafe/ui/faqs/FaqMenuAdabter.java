package com.example.boncafe.ui.faqs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.boncafe.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class FaqMenuAdabter extends RecyclerView.Adapter<FaqMenuAdabter.ViewHolder> {
    Context context;
    ArrayList<FaqModel> faqlistmodel;

    public FaqMenuAdabter(Context context, ArrayList<FaqModel> faqlistmodel) {
        this.context = context;
        this.faqlistmodel = faqlistmodel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.faqview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       try {
           holder.question.setText("Q "+position+" "+faqlistmodel.get(position+1).getQuestion());
           holder.ans.setText("A "+faqlistmodel.get(position+1).getAnswer());
       }catch (Exception e){

       }

    }

    @Override
    public int getItemCount() {
        return faqlistmodel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView ans;
    public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.id_question);
            ans = itemView.findViewById(R.id.id_anwser);

        }
    }
}
