package org.jokar.gankio.model.network.result;

import org.jokar.gankio.model.network.exception.APIException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by JokAr on 16/9/17.
 */
public class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(@NonNull HttpResult<T> tHttpResult) throws Exception {
        if(tHttpResult.isError()){
            throw new APIException();
        }
        return tHttpResult.getResults();
    }
}
