package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.AddGank;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by JokAr on 2016/9/23.
 */
public interface AddgankService {

    /**
     *
     * @param url   想要提交的网页地址
     * @param desc  对干货内容的描述
     * @param who   提交者 ID
     * @param type  干货类型 -可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param debug 当前提交为测试数据	 -如果想要测试数据是否合法, 请设置 debug 为 true! 可选参数: true | false
     * @return
     */
    @POST("add2gank")
    @FormUrlEncoded
    Observable<AddGank> addGank(@Field("url") String url,
                                @Field("desc") String desc,
                                @Field("who") String who,
                                @Field("type") String type,
                                @Field("debug") boolean debug);
}
