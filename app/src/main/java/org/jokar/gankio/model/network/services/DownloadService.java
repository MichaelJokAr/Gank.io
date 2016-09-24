package org.jokar.gankio.model.network.services;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by JokAr on 2016/9/24.
 */
public interface DownloadService {
    @Streaming
    @GET()
    Observable<ResponseBody> download(@Url String imageUrl);
}
