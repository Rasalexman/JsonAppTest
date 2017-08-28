package mincor.com.jsonapptest.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mincor.com.jsonapptest.application.MainApplication;
import mincor.com.jsonapptest.consts.Constants;
import mincor.com.jsonapptest.models.JsonData;
import mincor.com.jsonapptest.models.PostData;
import mincor.com.jsonapptest.utils.L;
import mincor.com.jsonapptest.utils.Support;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexander on 11.07.17.
 */

@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(new Cache(MainApplication.getInstance().getCacheDir(), 10 * 1024 * 1024)); // 10 MB Cache
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            if (Support.isNetworkAvailable()) {
                // if there is connectivity, we tell the request it can reuse the data for sixty seconds.
                request = request.newBuilder().header("Cache-Control", "public, max-age=" + Constants.NETWORK_AVAILABLE_AGE).build();
            } else {
                // If there’s no connectivity, we ask to be given only (only-if-cached) ‘stale’ data upto 7 days ago
                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + Constants.NETWORK_UNAVAILABLE_STALE).build();
            }
            return chain.proceed(request);
        });
        return httpClient.build();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PostData.class, new PostDataDeserializer())
                .create();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.API_URL)
                .client(okHttpClient)
                .build();
    }

    // This has nothing inside of it, it still works.
    public class PostDataDeserializer implements JsonDeserializer<PostData> {

        @Override
        public PostData deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws
                JsonParseException {

            Gson gson = new Gson();
            PostData postData = gson.fromJson(json, PostData.class);

            try{
                JsonElement jsons = json.getAsJsonObject().get("json");
                if (jsons.isJsonArray()) {
                    for (JsonElement element : jsons.getAsJsonArray()) {
                        postData.getJsons().add(gson.fromJson(element, JsonData.class));
                    }
                } else {
                    JsonElement element = jsons.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
                    for (Map.Entry<String, JsonElement> entry : entries) {
                        postData.getJsons().add(gson.fromJson(entry.getValue(), JsonData.class));
                    }
                }
            }catch(NullPointerException exc){
                L.d(exc.getMessage());
            }

            return postData;
        }
    }
}
