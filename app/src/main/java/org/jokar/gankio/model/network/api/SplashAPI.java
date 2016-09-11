package org.jokar.gankio.model.network.api;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.model.network.services.SplashService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashAPI {

    private Retrofit retrofit;
    private String base_url = "http://news-at.zhihu.com/api/4/";
    private static final int DEFAULT_TIMEOUT = 15;

    public SplashAPI() {
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


        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public void getImage(Subscriber<SplashImage> subscriber,
                         LifecycleTransformer lifecycleTransformer){

        retrofit.create(SplashService.class)
                .getImage()
                .compose(lifecycleTransformer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
