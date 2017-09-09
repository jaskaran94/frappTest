package com.example.frapptest.api;

import com.example.frapptest.models.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public interface ApiService {
    @GET("/favourite.json")
    Call<List<Model>> getData();
}
