package mincor.com.jsonapptest.controllers.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;

import butterknife.BindView;
import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.utils.recyclerview.EndlessRecyclerViewScrollListener;

/**
 * Created by Alex on 07.01.2017.
 */

public abstract class BaseHeadRecyclerController extends BaseController implements FastAdapter.OnClickListener<AbstractItem> {

    @Nullable @BindView(R.id.rv_controller)     protected RecyclerView rv_controller;
    @BindView(R.id.toolbar_controller)          protected Toolbar toolbar_controller;

    // manager
    protected RecyclerView.LayoutManager layoutManager;
    // save our FastAdapter
    protected FastItemAdapter<AbstractItem> mFastItemAdapter;
    // endless update adapter Item
    protected FooterAdapter<ProgressItem> mFooterAdapter;

    // Store a member variable for the listener
    protected RecyclerView.OnScrollListener scrollListener;

    public BaseHeadRecyclerController(){ }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_head_recycler, container, false);
    }

    protected RecyclerView getRVC()             {  return rv_controller;        }
    @Override protected Toolbar getToolbar()    {  return toolbar_controller;   }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        createAdapters();                   // создаем адаптеры
        setRVLayoutManager();               // назначаем лайаут
        setRVCAdapter();                    // назначаем адаптер
        addOnScrollListener();              // добавляем скролл
        checkData();                        // загружаем данные
        applyScrollPosition();              // назначаем сохраненную скролл позицию
    }

    protected void createAdapters(){
        mFastItemAdapter = new FastItemAdapter<>();
        mFastItemAdapter.withOnClickListener(this);
        mFooterAdapter = new FooterAdapter<>();
    }

    protected void setRVCAdapter(){
        getRVC().setHasFixedSize(true);
        getRVC().swapAdapter(mFooterAdapter.wrap(mFastItemAdapter), true);
    }

    protected void addOnScrollListener(){
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                getRVC().post(() -> {
                    if(totalItemsCount > 1) onLoadMoreBottomHandler(page);
                });
            }
        };
        getRVC().addOnScrollListener(scrollListener);
    }

    protected void scrollToFirstIfNeeded(){
        if(((LinearLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition() <= 1){
            layoutManager.scrollToPosition(0);
        }
        hideLoadingFooter();
    }

    protected void onLoadMoreBottomHandler(int currentPage){
        showLoadingFooter();
    }

    // Показываем загрузку
    protected void showLoadingFooter() {
        hideLoadingFooter();
        if(mFooterAdapter != null) mFooterAdapter.add(new ProgressItem().withEnabled(false));
    }
    // прячем загрузку
    protected void hideLoadingFooter(){
        if(mFooterAdapter != null) mFooterAdapter.clear();
    }

    // применяем последнюю позицию скролла
    protected void applyScrollPosition(){
        if(layoutManager != null){
            layoutManager.scrollToPosition(getPreviousPosition());
        }
    }

    @Override
    public boolean onClick(View v, IAdapter<AbstractItem> adapter, AbstractItem item, int position) {
        onItemClickHandler(position);
        return false;
    }

    protected void onItemClickHandler(int index){}

    protected void setRVLayoutManager(){
        layoutManager = new LinearLayoutManager(this.getActivity());
        ((LinearLayoutManager)layoutManager).setSmoothScrollbarEnabled(false);
        getRVC().setLayoutManager(layoutManager);
    }

    protected void checkData(){
        showLoadingFooter();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        setPreviousPosition(layoutManager);
        clearEndlessScrollListener();
        getRVC().getRecycledViewPool().clear();
        mFastItemAdapter.notifyDataSetChanged();

        getRVC().setAdapter(null);
        getRVC().removeAllViews();
        getRVC().removeAllViewsInLayout();
        getRVC().setLayoutManager(null);

        if(mFooterAdapter != null){
            mFooterAdapter.clear();
            mFooterAdapter = null;
        }
        if (mFastItemAdapter != null) {
            mFastItemAdapter.withEventHook(null);
            mFastItemAdapter.clear();
            mFastItemAdapter.withOnClickListener(null);
            mFastItemAdapter = null;
        }
        layoutManager = null;

        super.onDetach(view);
    }

    private void clearEndlessScrollListener(){
        if(scrollListener!=null){
            getRVC().clearOnScrollListeners();
            getRVC().removeOnScrollListener(scrollListener);
            scrollListener = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                getRouter().popController(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // если хотим сохранить последнюю проскролленную позицию
    protected void setPreviousPosition(RecyclerView.LayoutManager layoutManager){   }
    protected int getPreviousPosition(){   return 0;   }


}
