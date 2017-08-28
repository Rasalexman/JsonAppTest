package mincor.com.jsonapptest.di.services;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.di.interfaces.IApiService;
import mincor.com.jsonapptest.di.interfaces.PostApi;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.models.PostsList;
import mincor.com.jsonapptest.utils.L;
import mincor.com.jsonapptest.utils.Support;
import retrofit2.Retrofit;

/**
 * Created by alexander on 24.08.17.
 */

public final class ApiService implements IApiService {

    PostApi postApi;

    @Inject
    public ApiService(Retrofit retrofit){
        postApi = retrofit.create(PostApi.class);
    }

    @Override public Observable<List<PostData>> getPostData() {
        final int postRandomData = Support.randInt(1,3);
        L.d("postRandomData ",postRandomData);
        Observable<List<PostData>> postDataObservable;
        switch(postRandomData){
            case Constants.POST_ONE:
                postDataObservable = postApi.getPostData1();
                break;
            case Constants.POST_TWO:
                postDataObservable = postApi.getPostData2();
                break;
            case Constants.POST_THREE:
                postDataObservable = postApi.getPostData3();
                break;
            default: postDataObservable = postApi.getPostData1();
        }

        return postDataObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
