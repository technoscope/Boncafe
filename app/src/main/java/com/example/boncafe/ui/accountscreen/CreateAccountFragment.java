package com.example.boncafe.ui.accountscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boncafe.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.List;

import static com.example.boncafe.MainActivity.auth_key;
import static com.example.boncafe.MainActivity.key;


public class CreateAccountFragment extends Fragment {

    @BindView(R.id.id_firstname)
    EditText firstname;
    @BindView(R.id.id_lastname)
    EditText lastname;
    @BindView(R.id.id_create_email)
    EditText create_email;
    @BindView(R.id.id_pick_password)
    EditText pickpass;
    @BindView(R.id.id_confirm_password)
    EditText confirmpass;
    @BindView(R.id.id_phone_no)
    EditText phoneno;
    @BindView(R.id.id_gender)
    EditText gender;
    @BindView(R.id.id_date_of_birth)
    EditText dateofbirth;
    @BindView(R.id.continue_btn)
    Button confirmbtn;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitcreateaccount();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void hitcreateaccount() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String baseurl = "http://boncafe.botsolutions.org/public/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PlaceHolderApi jsonPlaceHolderApi = retrofit.create(PlaceHolderApi.class);
        Call<AccountModel> listCall = jsonPlaceHolderApi.CreateUserWithField(key, auth_key,
                firstname.getText().toString() + lastname.getText().toString(), create_email.getText().toString(), pickpass.getText().toString(),
                confirmpass.getText().toString(), phoneno.getText().toString(), "", "","Active");

        listCall.enqueue(new Callback<AccountModel>() {
            @Override
            public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                Toast.makeText(getActivity(), "Success"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AccountModel> call, Throwable t) {

                Toast.makeText(getActivity(), "" + "Account Created=" , Toast.LENGTH_SHORT).show();

            }
        });
    }
}