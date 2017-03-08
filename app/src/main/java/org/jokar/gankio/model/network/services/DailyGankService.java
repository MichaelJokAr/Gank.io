package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.GankDayEntities;
import org.jokar.gankio.model.network.result.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 每日干货api
 * Created by JokAr on 2016/9/24.
 */
public interface DailyGankService {

    /**
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<HttpResult<GankDayEntities>> day(@Path("year") String year,
                                                @Path("month") String month,
                                                @Path("day") String day);
}
