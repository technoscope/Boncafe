package com.example.boncafe.ui.rewards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.boncafe.R;
import com.example.boncafe.ui.accountscreen.AccountFragment;

import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    private RewardsViewModel galleryViewModel;
    private Button siginbtn;
    private GridView gridView;
    private ArrayList<RewardApiModel> rewardlist;
    private RewardModel rewardModel;
    private RewardsAdabter adabter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.id_rewards_gridview);
        rewardlist = new ArrayList<>();
        mCallRewardsApi();


        siginbtn = view.findViewById(R.id.id_rewards_sigin_btn);
        siginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = getFragmentManager();
                transaction.beginTransaction()
                        .replace(R.id.nav_host_fragment, new AccountFragment()) //<---replace a view in your layout (id: container) with the newFragment
                        .commit();
            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(RewardsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rewards, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void mCallRewardsApi() {
        String baseurl = "http://boncafe.botsolutions.org/public/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlaceHolderApi jsonPlaceHolderApi = retrofit.create(PlaceHolderApi.class);
        Call<List<RewardApiModel>> listCall = jsonPlaceHolderApi.getrewards();
        listCall.enqueue(new Callback<List<RewardApiModel>>() {
            @Override
            public void onResponse(Call<List<RewardApiModel>> call, Response<List<RewardApiModel>> response) {
                List<RewardApiModel> models = response.body();
                for(int i=1; i<models.size();i++){
                    RewardApiModel model = new RewardApiModel();
                    model.setEnd(models.get(i).getEnd());
                    model.setStart(models.get(i).getStart());
                    model.setUrl(models.get(i).getUrl());
                    rewardlist.add(model);
                }
                adabter = new RewardsAdabter(getContext(), rewardlist);
                gridView.setAdapter(adabter);

            }

            @Override
            public void onFailure(Call<List<RewardApiModel>> call, Throwable t) {

            }


        });
    }
}