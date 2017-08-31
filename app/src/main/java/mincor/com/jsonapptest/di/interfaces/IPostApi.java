package mincor.com.jsonapptest.di.interfaces;

import java.util.List;

import io.reactivex.Observable;
import mincor.com.jsonapptest.models.PostData;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alexander on 24.08.17.
 */

public interface IPostApi {
    @GET("materialjson/file_{page}.json")
    Observable<List<PostData>> getPostData(@Path("page") int page);
}
