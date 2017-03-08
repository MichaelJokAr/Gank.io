package org.jokar.gankio.model.network.services;

import org.jokar.gankio.model.entities.SearchEntities;
import org.jokar.gankio.model.network.result.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by JokAr on 16/9/17.
 */
public interface SearchService {

    @GET("search/query/listview/category/{type}/count/{countSize}/page/{pageSize} ")
    Observable<HttpResult<List<SearchEntities>>> search(@Path("type") String type,
                                                        @Path("countSize") int countSize,
                                                        @Path("pageSize") int pageSize);
}
