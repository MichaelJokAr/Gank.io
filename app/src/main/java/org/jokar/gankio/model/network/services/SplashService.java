package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.SplashImage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashService {

    @GET("start-image/1080*1776")
    Observable<SplashImage> getImage();


}
