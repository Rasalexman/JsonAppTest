package mincor.com.jsonapptest.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.application.MainApplication;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.controllers.base.BaseHeadRecyclerController;
import mincor.com.jsonapptest.di.interfaces.IApiService;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.L;
import mincor.com.jsonapptest.utils.Support;
import mincor.com.jsonapptest.view.PostItem;

/**
 * Created by alexander on 24.08.17.
 */

public class StartController extends BaseHeadRecyclerController {

    @Inject IApiService apiService;

    @BindString(R.string.app_name) String app_name;
    @BindString(R.string.warning_head) String warning_head;
    @BindString(R.string.error_message_404) String error_message_404;

    @Override
    protected String getTitle() {
        return app_name;
    }

    public StartController(){
        MainApplication.getAppComponent().inject(this);
    }

    @Override
    protected void checkData() {
        if(apiService.getAllPosts().size() > 0){
            addItemsToAdapter(apiService.getAllPosts());
        }else{
            onLoadMoreBottomHandler(1);
        }
    }

    @Override protected void onItemClickHandler(int index) {
        L.d("ON MORE CLICK HANDLER");
        DescController descController = new DescController(apiService.getAllPosts().get(index));
        this.getRouter().pushController(RouterTransaction.with(descController)
                .tag(Constants.TAG_DESC_CONTROLLER)
                .pushChangeHandler(new HorizontalChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler()));
    }

    @Override
    protected void onLoadMoreBottomHandler(int currentPage) {
        super.onLoadMoreBottomHandler(currentPage);
        apiService.getPostData(currentPage).subscribe(
                postDatas ->
                    addItemsToAdapter(postDatas),
                throwable -> {
                    L.d(throwable.getMessage());
                    hideLoadingFooter();
                    Support.alertDisplayerWithOK(this.getActivity(), warning_head, error_message_404, null);
                });
    }

    private void addItemsToAdapter(List<PostData> items) {
        for (PostData postData : items){
            mFastItemAdapter.add(new PostItem().withPostData(postData));
        }
        scrollToFirstIfNeeded();
    }

    @Override
    protected void setPreviousPosition(RecyclerView.LayoutManager layoutManager) {
        Constants.PREVIOUS_POST_POSITION = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
    }

    @Override
    protected int getPreviousPosition() {
        return Constants.PREVIOUS_POST_POSITION;
    }
}
