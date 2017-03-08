package org.jokar.gankio.di.module.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.model.network.down.DownloadProgressInterceptor;
import org.jokar.gankio.model.network.down.DownloadProgressListener;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JokAr on 2016/9/24.
 */
@Module
public class DownNetModule {

    private DownloadProgressListener mListener;
    private static final int DEFAULT_TIMEOUT = 15;

    public DownNetModule(DownloadProgressListener listener) {
        mListener = listener;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(){
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(mListener);

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
