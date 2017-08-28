package mincor.com.jsonapptest.controllers.actionbar;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

public interface ActionBarProvider {
    ActionBar getSupportActionBar();
    void setSupportActionBar(@Nullable Toolbar toolbar);
}
