package com.monik.jain.engineeringai.API;


import com.monik.jain.engineeringai.models.Posts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search_by_date?tags=story")
    Call<Posts> getPosts(@Query("page") int page);

}