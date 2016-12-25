package rest;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import models.GitResult;
import models.Item;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public class RestClient {

    private static GitApiInterface gitApiInterface ;
    private static String baseUrl = "https://api.github.com" ;

    public static GitApiInterface getClient() {
        if (gitApiInterface == null) {

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverter(String.class, new ToStringConverter())
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gitApiInterface = client.create(GitApiInterface.class);
        }
        return gitApiInterface ;
    }

    public interface GitApiInterface {

        @GET("/search/users")
        Call<GitResult> getUsersNamed(@Query("q") String name);

        @POST("/user/create")
        Call<Item> createUser(@Body String name, @Body String email);

        @PUT("/user/{id}/update")
        Call<Item> updateUser(@Path("id") String id, @Body Item user);
    }
}
