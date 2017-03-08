package org.jokar.gankio.model.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.model.entities.DataEntities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JokAr on 16/9/19.
 */
public interface DataModel {

    interface DataCallBack{
        void start(boolean hasLocalData, List<DataEntities> dataEntitiesList);

        void requestSuccess(ArrayList<DataEntities> dataEntitiesList);

        void requestFail(boolean hasLocalData, Throwable e);

        void onCompleted();
    }

    /**
     * 请求数据
     * @param type  数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param count 请求个数： 数字，大于0
     * @param pageSize 第几页：数字，大于0
     * @param dataDB
     * @param lifecycleTransformer
     * @param callBack
     */
    void request(String type,
                 int count,
                 int pageSize,
                 DataDB dataDB,
                 LifecycleTransformer lifecycleTransformer,
                 DataCallBack callBack);

    /**
     * 请求数据
     * @param type  数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param count 请求个数： 数字，大于0
     * @param pageSize 第几页：数字，大于0
     * @param dataDB
     * @param lifecycleTransformer
     * @param callBack
     */
    void refresh(String type,
                 int count,
                 int pageSize,
                 DataDB dataDB,
                 LifecycleTransformer lifecycleTransformer,
                 DataCallBack callBack);
}
