package ru.itis.android.books.presenter;

import ru.itis.android.books.model.bean.SearchResult;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.rxloader.RxLoader;
import me.tatarka.rxloader.RxLoaderManager;
import me.tatarka.rxloader.RxLoaderObserver;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.model.bean.surrogate.SearchResultWrapper;
import ru.itis.android.books.model.database.wrapper.ArticleWrapper;
import ru.itis.android.books.view.base.MainView;
import ru.itis.android.books.www.search.ArticleSearchApiInterface;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Users on 21.12.2017.
 */

public class Presenter {
    private static final String LOADER_SELECT_PREV = "select";
    private static final String LOADER_SEARCH = "search";

    private static Presenter presenter;

    private List<Article> articles;

    private MainView view;
    private ArticleSearchApiInterface retrofitService;
    private RxLoaderManager loaderManager;
    private DatabaseManager databaseManager;
    private RxLoader loader;

    private Presenter(@NonNull ArticleSearchApiInterface articleSearchApiInterface,
                      @NonNull MainView view,
                      @NonNull RxLoaderManager loaderManager,
                      @NonNull DatabaseManager databaseManager) {
        this.retrofitService = articleSearchApiInterface;
        this.view = view;
        this.loaderManager = loaderManager;
        this.databaseManager = databaseManager;
    }

//------------------------------------------Presenter API-------------------------------------------
    public static Presenter getInstance(@NonNull ArticleSearchApiInterface articleSearchApiInterface,
                                        @NonNull MainView view,
                                        @NonNull RxLoaderManager loaderManager,
                                        @NonNull DatabaseManager databaseManager) {
        if (presenter == null) {
            presenter = new Presenter(articleSearchApiInterface, view,loaderManager,databaseManager);
        }
        return presenter;
    }

    public void searchByKeyWord(String keyWord){
        loader = loaderManager.create(LOADER_SEARCH,
                retrofitService
                        .getArticlesByKeyWord(ArticleSearchApiInterface.necessaryInformation,
                                keyWord,
                                ArticleSearchApiInterface.API_KEY)
                        .flatMap(new Func1<SearchResult, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(SearchResult result) {
                                SearchResultWrapper searchResult = new SearchResultWrapper(result);
                                articles = searchResult.getArticles();
                                return  Observable
                                        .concat(databaseManager.getArgsClearQuery(),
                                                databaseManager.getArticlesInsertQuery(articles));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<Long>() {
                    @Override
                    public void onNext(Long value) {}

                    @Override
                    public void onError(Throwable e) {
                        view.onSearchError(e.getMessage());
                        loader.clear();
                    }

                    @Override
                    public void onCompleted() {
                        view.onSearchSuccess(articles);
                        loader.clear();
                    }
                }).restart();
    }

    public void loadPreviousSearchResult(){
        loader = loaderManager.create(LOADER_SELECT_PREV,
                databaseManager.getArticlesSelectQuery()
                        .map(new Func1<SqlBrite.Query, List<Article>>() {
                            @Override
                            public List<Article> call(SqlBrite.Query query) {
                                Cursor cursor = query.run();
                                articles = new ArrayList<>();
                                if (cursor != null) {
                                    ArticleWrapper wrapper = new ArticleWrapper(cursor);
                                    articles.addAll(wrapper.getArticles());
                                    cursor.close();
                                }
                                return articles;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<List<Article>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.onLoadingPreviousSearchResultError(e.getMessage());
                        loader.clear();
                    }
                    @Override
                    public void onNext(List<Article> articles) {
                        view.onLoadingPreviousSearchResultSuccess(articles);
                    }

                    @Override
                    public void onCompleted() {
                        loader.clear();
                    }
                }).start();
    }

//---------------------------------------Многопоточность--------------------------------------------
    /*@Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_SEARCH_ARTICLES:
                return new SearchLoader(
                        activity.getApplicationContext(),
                        ((ArticlesApp) application).getSearchApi(),
                        this,
                        args.getString(ARGS_SEARCH_KEY_WORD));
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, LoaderSearchResult loaderSearchResult) {
        if (loaderSearchResult != null) {
            switch (loader.getId()) {
                case LOADER_ID_SEARCH_ARTICLES:
                    articleListViewInterface.showSearchResult(loaderSearchResult);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }*/

//------------------------------------Обработка HTTP-ответа-----------------------------------------
   /* @Override
    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
        if (response.isSuccessful()) {
            SearchResult searchResult = response.body();
            onLoadFinished(
                    loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                    new LoaderSearchResult(new SearchResultWrapper(searchResult)));
        } else {
            try {
                onLoadFinished(
                        loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                        new LoaderSearchResult(response.errorBody().string())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        onLoadFinished(
                loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                new LoaderSearchResult(t.getMessage())
        );
    }*/
}
