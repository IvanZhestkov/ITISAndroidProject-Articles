package ru.itis.android.books.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.text.SimpleDateFormat;
import java.util.List;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.table.ArticleTable;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class DatabaseManager {

    private BriteDatabase database;

    public DatabaseManager(@NonNull Context context) {
        SqlBrite sqlBrite = SqlBrite.create();
        database = sqlBrite.wrapDatabaseHelper(new DatabaseHelper(context), Schedulers.io());
    }

    @NonNull
    public Observable<Long> getArgsClearQuery(){
        return Observable.just((long)database.delete(ArticleTable.NAME, null));
    }

    @NonNull
    public Observable<Long> getArticlesInsertQuery(@NonNull final List<Article> articles){
        return Observable
                .from(articles)
                .map(new Func1<Article, Long>() {
                    @Override
                    public Long call(Article article) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ArticleTable.COLUMN_HEADLINE, article.getHeadLine());
                        contentValues.put(ArticleTable.COLUMN_SNIPPET,article.getSnippet());
                        contentValues.put(ArticleTable.COLUMN_ARTICLE_URL,article.getWebURL());
                        contentValues.put(ArticleTable.COLUMN_IMAGE_URL,article.getImageURL());
                        contentValues.put(ArticleTable.COLUMN_AUTHOR,article.getAuthor());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String pubDateString = format.format(article.getPublicationDate());
                        contentValues.put(ArticleTable.COLUMN_PUBLICATION_DATE,pubDateString);
                        return database.insert(ArticleTable.NAME, contentValues);
                    }
                });
    }

    @NonNull
    public Observable<SqlBrite.Query> getArticlesSelectQuery(){
        return database.createQuery(ArticleTable.NAME, ArticleTable.getSelectQuery());
    }
}
