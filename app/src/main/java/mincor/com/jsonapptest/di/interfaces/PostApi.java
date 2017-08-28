package mincor.com.jsonapptest.di.interfaces;

import java.util.List;

import io.reactivex.Observable;
import mincor.com.jsonapptest.models.PostData;
import retrofit2.http.GET;

/**
 * Created by alexander on 24.08.17.
 */

public interface PostApi {
    @GET("materialjson/file_1.json")
    Observable<List<PostData>> getPostData1();

    @GET("materialjson/file_2.json")
    Observable<List<PostData>> getPostData2();

    @GET("materialjson/file_3.json")
    Observable<List<PostData>> getPostData3();
}
