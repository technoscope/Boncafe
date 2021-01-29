package com.example.boncafe.ui.accountscreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boncafe.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import static com.example.boncafe.MainActivity.auth_key;
import static com.example.boncafe.MainActivity.key;

public class AccountFragment extends Fragment {

    @BindView(R.id.id_create_account)
    TextView createaccount;
    @BindView(R.id.id_ac_forgot_pass)
    TextView forgetpass;
    @BindView(R.id.id_ac_email)
    EditText email;
    @BindView(R.id.id_ac_password)
    EditText password;
    @BindView(R.id.id_ac_login_btn)
    Button loginbtn;
    private Unbinder unbinder;

    private String uri = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new PassForgotFragment());
                transaction.addToBackStack("Sign in Page");
                transaction.commit();
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new CreateAccountFragment());
                transaction.addToBackStack("CreateAccountPage in Page");
                transaction.commit();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallLoginApis();
            }
        });
        return view;
    }

    private void mCallLoginApis() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String baseurl = "http://boncafe.botsolutions.org/public/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PlaceHolderApi jsonPlaceHolderApi = retrofit.create(PlaceHolderApi.class);
        Call<loginModel> listCall = jsonPlaceHolderApi.LoginUserWithField(key, auth_key,
                email.getText().toString(),
                password.getText().toString());
        listCall.enqueue(new Callback<loginModel>() {
            @Override
            public void onResponse(Call<loginModel> call, Response<loginModel> response) {
                Toast.makeText(getActivity(), " login Success" + response.body(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<loginModel> call, Throwable t) {
                Toast.makeText(getActivity(), "login success "
                        , Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}