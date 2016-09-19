package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.network.result.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

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
    Observable<HttpResult<List<DataEntities>>> getData(@Path("type") String type,
                                                       @Path("count") int count,
                                                       @Path("pageSize") int pageSize);
}
