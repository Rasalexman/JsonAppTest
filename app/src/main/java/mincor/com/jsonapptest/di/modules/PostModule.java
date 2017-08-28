package mincor.com.jsonapptest.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mincor.com.jsonapptest.di.interfaces.IApiService;
import mincor.com.jsonapptest.di.services.ApiService;
import retrofit2.Retrofit;

/**
 * Created by alexander on 24.08.17.
 */
@Module
public class PostModule {
    @Provides
    @Singleton
    IApiService provideApiService(Retrofit retrofit){
        return new ApiService(retrofit);
    }
}
