package com.example.myapplication.api;

import android.content.Context; // Va trebui să trimitem Context-ul la client
import com.example.myapplication.utils.TokenManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static Retrofit retrofit = null;


    private static TokenManager tokenManager;


    public static void initialize(Context context) {
        tokenManager = new TokenManager(context);
    }

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {


            Interceptor authInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();


                    String token = tokenManager.getToken();


                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", (token != null) ? "Bearer " + token : "");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(serviceClass);
    }


    public static AuthService getAuthService() {
        return createService(AuthService.class);
    }
}