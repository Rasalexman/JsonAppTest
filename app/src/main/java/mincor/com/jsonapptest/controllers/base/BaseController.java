package mincor.com.jsonapptest.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mincor.com.jsonapptest.controllers.actionbar.ActionBarProvider;

public abstract class BaseController extends RefWatchingController {

    public BaseController(){
        super();
    }

    public BaseController(Bundle args) {
        super(args);
    }

    protected Toolbar getToolbar(){
        return null;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        attachListeners();
        setTitle();
        setActionBar();
        this.setHasOptionsMenu(true);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        detachListeners();
        super.onDetach(view);
    }

    /**
     * Назначаем слушателей для текущего Контроллера
     */
    protected void attachListeners(){

    }

    /**
     * Удаляем слушателей для текущего контроллера
     */
    protected void detachListeners(){

    }

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected ActionBar getActionBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider) getActivity());
        return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
    }

    protected void setActionBar(){
        ((ActionBarProvider)getActivity()).setSupportActionBar(getToolbar());
    }

    protected void setTitle(){
        if(getToolbar() != null) getToolbar().setTitle(getTitle());
    }
    protected String getTitle() {
        return "";
    }
}
