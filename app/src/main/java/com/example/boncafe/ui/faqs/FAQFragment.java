package com.example.boncafe.ui.faqs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boncafe.R;

import java.util.ArrayList;
import java.util.List;

public class FAQFragment extends Fragment {

    RecyclerView recyclerView;
    FaqMenuAdabter adapter;
    ArrayList<FaqModel> faqlistmodels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_a_q, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.id_______recycle___view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        faqlistmodels = new ArrayList<>();
        mCallLoginApis();

    }

    private void mCallLoginApis() {
        String baseurl = "http://boncafe.botsolutions.org/public/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiPlaceHolder jsonPlaceHolderApi = retrofit.create(ApiPlaceHolder.class);
        Call<List<FaqModel>> listCall = jsonPlaceHolderApi.FAQ_MODEL_CALL("5", "D3phbZlLWDm0hRxe");
        listCall.enqueue(new Callback<List<FaqModel>>() {
            @Override
            public void onResponse(Call<List<FaqModel>> call, Response<List<FaqModel>> response) {
                List<FaqModel> models = response.body();
                for (FaqModel data : models) {
                    FaqModel model = new FaqModel();
                    model.setQuestion(data.getQuestion());
                    model.setAnswer(data.getAnswer());
                    faqlistmodels.add(model);
                }
                adapter = new FaqMenuAdabter(getContext(), faqlistmodels);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<FaqModel>> call, Throwable t) {

            }
        });
    }
}