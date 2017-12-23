package ru.itis.android.books.view.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.R;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;
import ru.itis.android.books.view.base.BaseFragment;
import ru.itis.android.books.view.base.MainView;

/**
 * Created by User on 22.12.2017.
 */

// TODO подгрузка картинок из сети (GLIDE)
public class MainFragment extends BaseFragment implements MainView {

    private RecyclerView recyclerView;

    private LinearLayoutManager manager;

    private Adapter adapter;

    private MaterialSearchView searchView;

    protected Presenter presenter;

    private List<Article> articles;

    @NonNull
    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        presenter = Presenter.getInstance(
                ((ArticlesApp) getActivity().getApplication()).getSearchApi(),
                this,
                RxLoaderManager.get(this),
                new DatabaseManager(getActivity()));


        presenter.loadPreviousSearchResult();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        searchView = v.findViewById(R.id.search_view);

        recyclerView = v.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showSearch(false);
        searchView.clearFocus();
    }

    @Override
    public void onSearchSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат поиска
    }

    @Override
    public void onSearchError(String message) {
        // TODO отобразить ошибку при загруке стетей
    }

    @Override
    public void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат данных из кэша(бд)
    }

    @Override
    public void onLoadingPreviousSearchResultError(String message) {
        // TODO отобразить ошибку при загруке из кэша(бд)
    }

    // TODO запустить событие поиска статьи:
    // presenter.searchByKeyWord(keyWord);
}
