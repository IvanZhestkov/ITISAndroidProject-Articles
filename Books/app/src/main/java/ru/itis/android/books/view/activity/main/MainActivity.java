package ru.itis.android.books.view.activity.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.R;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;
import ru.itis.android.books.view.WebViewActivity;
import ru.itis.android.books.view.base.BaseActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity implements OnArticleClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    private Toolbar toolbar;

    private Unbinder unbinder;

    private List<Article> articles;

    private LinearLayoutManager manager;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = Presenter.getInstance(
                ((ArticlesApp) getApplication()).getSearchApi(),
                this,
                RxLoaderManager.get(this),
                new DatabaseManager(this));


        presenter.loadPreviousSearchResult();

        initView();
    }

    private void initView() {
        toolbar = findViewById(getToolbarId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        unbinder = ButterKnife.bind(this);
        manager = new LinearLayoutManager(this);
        adapter = new Adapter(this, this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showSearch(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchByKeyWord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Article article = articles.get(position);
        startActivity(WebViewActivity.makeInflatedIntent(this, article.getWebURL()));
    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Are you sure you want to exit the application?");

        quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        quitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

    @Override
    public void onSearchSuccess(@NonNull List<Article> articlez) {
        articles = articlez;
        adapter.setArticles(articles);
    }

    @Override
    public void onSearchError(String message) {
        Toast.makeText(this, "Error Loading", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> articlez) {
        articles = articlez;
        adapter.setArticles(articles);
    }

    @Override
    public void onLoadingPreviousSearchResultError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
