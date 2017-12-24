package ru.itis.android.books.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;


import ru.itis.android.books.R;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.presenter.Presenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by User on 11.12.2017.
 */

// TODO подгрузка картинок из сети (GLIDE)
public abstract class BaseActivity extends AppCompatActivity implements MainView {

    protected Toolbar toolbar;

    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

    }

    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public abstract void onSearchSuccess(@NonNull List<Article> articles);

    @Override
    public abstract void onSearchError(String message);

    @Override
    public abstract void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> articles);

    @Override
    public abstract void onLoadingPreviousSearchResultError(String message);
}
