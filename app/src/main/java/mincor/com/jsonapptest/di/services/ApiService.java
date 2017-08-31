package mincor.com.jsonapptest.di.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mincor.com.jsonapptest.di.interfaces.IApiService;
import mincor.com.jsonapptest.di.interfaces.IPostApi;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.L;
import retrofit2.Retrofit;

/**
 * Created by alexander on 24.08.17.
 */

public final class ApiService implements IApiService {

    private List<PostData> allPosts = new ArrayList<>();
    public List<PostData> getAllPosts() {
        return allPosts;
    }

    IPostApi postApi;

    @Inject
    public ApiService(Retrofit retrofit){
        postApi = retrofit.create(IPostApi.class);
    }

    @Override public Observable<List<PostData>> getPostData(int pageNumber) {
        L.d("postRandomData ",pageNumber);
        return postApi.getPostData(pageNumber).map(postDatas -> {
            allPosts.addAll(postDatas);
            return postDatas;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
