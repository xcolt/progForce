package rest;

import models.ReposAdapter;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ReposClient {
    @GET("users/{owner}/repos")
    Call<List<ReposAdapter>> repoContributors(
            @Path("owner") String owner);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

