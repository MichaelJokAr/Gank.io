package org.jokar.gankio.di.module.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jokar.gankio.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JokAr on 16/9/16.
 */
@Module
public class NetModule {

    private static final int DEFAULT_TIMEOUT = 15;


    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.LOG_DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
