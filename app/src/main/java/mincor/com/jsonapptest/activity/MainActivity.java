package mincor.com.jsonapptest.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;

import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.controllers.StartController;
import mincor.com.jsonapptest.controllers.actionbar.ActionBarProvider;

public class MainActivity extends AppCompatActivity implements ActionBarProvider {

    private Router mainRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define app router for conductor
        ViewGroup container = (ViewGroup) findViewById(R.id.controller_container);
        mainRouter = Conductor.attachRouter(this, container, savedInstanceState);
        if(!mainRouter.hasRootController()){
            mainRouter.setRoot(RouterTransaction.with(new StartController()));
        }

        Controller descController = mainRouter.getControllerWithTag(Constants.TAG_DESC_CONTROLLER);
        if(descController != null){
            mainRouter.pushController(RouterTransaction.with(descController).popChangeHandler(new HorizontalChangeHandler()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }
}
