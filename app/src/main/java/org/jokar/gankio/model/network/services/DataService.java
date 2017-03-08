package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.network.result.HttpResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JokAr on 16/9/19.
 */
public interface DataService {

    /**
     *
     * @param type      数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param count     请求个数： 数字，大于0
     * @param pageSize  第几页：数字，大于0
     * @return
     */
    @GET("data/{type}/{count}/{pageSize}")
    Observable<HttpResult<ArrayList<DataEntities>>> getData(@Path("type") String type,
                                                            @Path("count") int count,
                                                            @Path("pageSize") int pageSize);
}
