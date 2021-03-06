package mincor.com.jsonapptest.di.interfaces;

import java.util.List;

import io.reactivex.Observable;
import mincor.com.jsonapptest.models.PostData;

/**
 * Created by alexander on 24.08.17.
 */

public interface IApiService {
    List<PostData>              getAllPosts();
    Observable<List<PostData>>  getPostData(int pageNumber);

}
