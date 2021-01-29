package com.example.boncafe.ui.cafe;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;

interface JsonPlaceHolderApi {
    @GET("/api/branches.php/")
    Call<List<BranchesModel>> getbranches();
}
