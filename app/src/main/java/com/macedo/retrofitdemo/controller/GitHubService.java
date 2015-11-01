package com.macedo.retrofitdemo.controller;

import com.macedo.retrofitdemo.model.GitModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Beatriz on 01/11/2015.
 */
public interface GitHubService {

    public static final String BASE_URI = "https://api.github.com";

    @GET("/users/{user}")      //here is the other url part.best way is to start using /
    Call<GitModel> getFeed(@Path("user") String user);     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO
}