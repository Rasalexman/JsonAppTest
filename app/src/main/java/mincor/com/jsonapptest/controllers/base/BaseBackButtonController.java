package mincor.com.jsonapptest.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import mincor.com.jsonapptest.R;

/**
 * Created by Alex on 29.12.2016.
 */

public abstract class BaseBackButtonController extends BaseController {

    public BaseBackButtonController(){
        super();
    }

    protected BaseBackButtonController(Bundle args) {
        super(args);
    }

    @BindView(R.id.toolbar_controller)    Toolbar addQuestionToolBar;

    @Override    protected Toolbar getToolbar() {           return addQuestionToolBar;    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setHomeButtonEnable();
    }

    protected void setHomeButtonEnable() {
        //set the back arrow in the toolbar
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void goBack(){
        getRouter().popController(this);
    }
}
