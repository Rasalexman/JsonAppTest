package mincor.com.jsonapptest.di.component;

import javax.inject.Singleton;

import dagger.Component;
import mincor.com.jsonapptest.controllers.StartController;
import mincor.com.jsonapptest.di.modules.NetModule;
import mincor.com.jsonapptest.di.modules.PostModule;

/**
 * Created by Alex on 24.08.2017.
 */

@Singleton
@Component(modules = {NetModule.class, PostModule.class})
public interface AppComponent {
    void inject(StartController startController);
}
