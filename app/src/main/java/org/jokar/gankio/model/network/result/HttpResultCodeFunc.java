package org.jokar.gankio.model.network.result;

import org.jokar.gankio.model.entities.AddGank;
import org.jokar.gankio.model.network.exception.APIException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by JokAr on 2016/9/23.
 */

public class HttpResultCodeFunc implements Function<AddGank,String> {

    /**
     * Apply some calculation to the input value and return some other value.
     *
     * @param stringHttpResult the input value
     * @return the output value
     * @throws Exception on error
     */
    @Override
    public String apply(@NonNull AddGank stringHttpResult) throws Exception {
        if (stringHttpResult.isError()){
            throw new APIException(stringHttpResult.getMsg());
        }
        return stringHttpResult.getMsg();
    }
}
