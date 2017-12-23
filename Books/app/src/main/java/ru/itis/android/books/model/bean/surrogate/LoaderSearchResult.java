package ru.itis.android.books.model.bean.surrogate;

import android.support.annotation.Nullable;

/**
 * Created by Users on 21.12.2017.
 */

public class LoaderSearchResult {
    private SearchResultWrapper searchResultWrapper;
    private String errorMessage;

    // Результат успешной загрузки
    public LoaderSearchResult(SearchResultWrapper searchResultWrapper) {
        this.searchResultWrapper = searchResultWrapper;
        this.errorMessage = null;
    }

    // Результат безуспешной загрузки
    public LoaderSearchResult(String errorMessage) {
        this.errorMessage = errorMessage;
        this.searchResultWrapper = null;
    }

    @Nullable
    public SearchResultWrapper getSearchResultWrapper() {
        return searchResultWrapper;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }
}
