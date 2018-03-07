package com.example.ayabeltran.firstproject;

import java.util.*;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ayabeltran on 07/03/2018.
 */

public interface UserClient {

    @GET("/{user}")
    Call<List<UserList>> reposForUser(
    @Path("user") String user
    );

    @POST("posts")
    Call<UserList> adduser(@Body UserList posts);

}
