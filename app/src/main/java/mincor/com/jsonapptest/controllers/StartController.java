package mincor.com.jsonapptest.controllers;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.application.MainApplication;
import mincor.com.jsonapptest.controllers.base.BaseHeadRecyclerController;
import mincor.com.jsonapptest.di.interfaces.IApiService;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.L;
import mincor.com.jsonapptest.view.PostItem;

/**
 * Created by alexander on 24.08.17.
 */

public class StartController extends BaseHeadRecyclerController {

    @Inject
    IApiService apiService;

    private List<PostData> allPostData = new ArrayList<>();

    @BindString(R.string.app_name) String app_name;

    @Override
    protected String getTitle() {
        return app_name;
    }

    public StartController(){
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    protected void checkData() {
        if(allPostData.size() > 0){
            addItemsToAdapter(allPostData);
        }else{
            onLoadMoreBottomHandler(0);
        }
    }

    @Override protected void onItemClickHandler(int index) {
        L.d("ON MORE CLICK HANDLER");
        DescController descController = new DescController();
        descController.setPostData(allPostData.get(index));
        this.getRouter().pushController(RouterTransaction.with(descController)
                .pushChangeHandler(new HorizontalChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler()));
    }

    @Override
    protected void onLoadMoreBottomHandler(int currentPage) {
        super.onLoadMoreBottomHandler(currentPage);
        apiService.getPostData().subscribe(
                postDatas -> {
                    allPostData.addAll(postDatas);
                    addItemsToAdapter(postDatas);
                },
                throwable -> L.d(throwable.getMessage()));
    }

    private void addItemsToAdapter(List<PostData> items) {
        for (PostData postData : items){
            mFastItemAdapter.add(new PostItem().withPostData(postData));
        }
        scrollToFirstIfNeeded();
    }
}
