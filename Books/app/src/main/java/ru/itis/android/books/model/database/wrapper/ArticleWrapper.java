package ru.itis.android.books.model.database.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.table.ArticleTable;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class ArticleWrapper extends CursorWrapper {

    public ArticleWrapper(Cursor cursor) {
        super(cursor);
    }

    @Nullable
    private Article getArticle(){
        if(!isBeforeFirst() && !isAfterLast()) {
            String headLine = getString(getColumnIndex(ArticleTable.COLUMN_HEADLINE));
            String snippet = getString(getColumnIndex(ArticleTable.COLUMN_SNIPPET));
            String webUrl = getString(getColumnIndex(ArticleTable.COLUMN_ARTICLE_URL));
            String imageUrl = getString(getColumnIndex(ArticleTable.COLUMN_IMAGE_URL));
            String author = getString(getColumnIndex(ArticleTable.COLUMN_AUTHOR));

            String pubDateString = getString(getColumnIndex(ArticleTable.COLUMN_PUBLICATION_DATE));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date pubDate = null;
            try {
                pubDate = format.parse(pubDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Article(imageUrl, webUrl, headLine, snippet, author, pubDate);
        }
        return null;
    }

    @NonNull
    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();
        moveToFirst();
        while (!isBeforeFirst() && !isAfterLast()){
            Article article = getArticle();
            if(article != null) {
                articles.add(article);
            }
            moveToNext();
        }
        return articles;
    }
}
