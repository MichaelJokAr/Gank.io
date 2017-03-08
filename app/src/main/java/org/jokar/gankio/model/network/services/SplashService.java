package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.SplashImage;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashService {

    @GET("start-image/1080*1776")
    Observable<SplashImage> getImage();


}
