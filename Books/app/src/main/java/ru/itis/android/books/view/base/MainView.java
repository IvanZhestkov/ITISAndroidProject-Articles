package ru.itis.android.books.view.base;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.android.books.model.bean.Article;


public interface MainView {

    void onSearchSuccess(@NonNull List<Article> responseObject);

    void onSearchError(String message);

    void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> responseObject);

    void onLoadingPreviousSearchResultError(String message);

}
