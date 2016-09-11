package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.SplashImage;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashService {

    @GET("start-image/1080*1776")
    Observable<SplashImage> getImage();
}
