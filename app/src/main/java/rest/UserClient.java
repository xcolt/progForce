package rest;

import models.TargetAdapter;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.GsonConverterFactory;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UserClient {
    @GET("users/{owner}")
    Call<TargetAdapter> userContributors(
            @Path("owner") String owner);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

