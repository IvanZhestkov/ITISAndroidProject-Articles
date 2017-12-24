package ru.itis.android.books.app;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.itis.android.books.R;
import ru.itis.android.books.www.search.ArticleSearchApiInterface;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Users on 21.12.2017.
 */

public class ArticlesApp extends Application {
    private ArticleSearchApiInterface searchApiInterface;

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/georgia.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(ArticleSearchApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        searchApiInterface = retrofit.create(ArticleSearchApiInterface.class);
    }

    public ArticleSearchApiInterface getSearchApi() {
        return searchApiInterface;
    }
}
