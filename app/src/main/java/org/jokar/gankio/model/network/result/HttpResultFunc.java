package org.jokar.gankio.model.network.result;

import org.jokar.gankio.model.network.exception.APIException;

import rx.functions.Func1;

/**
 * Created by JokAr on 16/9/17.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if(tHttpResult.isError()){
            throw new APIException();
        }
        return tHttpResult.getResults();
    }
}
