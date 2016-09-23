package org.jokar.gankio.model.network.result;

import org.jokar.gankio.model.entities.AddGank;
import org.jokar.gankio.model.network.exception.APIException;

import rx.functions.Func1;

/**
 * Created by JokAr on 2016/9/23.
 */

public class HttpResultCodeFunc implements Func1<AddGank,String> {
    @Override
    public String call(AddGank stringHttpResult) {
        if (stringHttpResult.isError()){
            throw new APIException(stringHttpResult.getMsg());
        }
        return stringHttpResult.getMsg();
    }
}
