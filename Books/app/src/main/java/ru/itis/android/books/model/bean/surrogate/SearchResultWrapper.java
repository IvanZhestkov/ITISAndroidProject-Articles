package ru.itis.android.books.model.bean.surrogate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.bean.Doc;
import ru.itis.android.books.model.bean.Multimedium;
import ru.itis.android.books.model.bean.SearchResult;
import ru.itis.android.books.www.search.ArticleSearchApiInterface;

/**
 * Created by Users on 21.12.2017.
 */

public class SearchResultWrapper {
    private SearchResult searchResult;

    private List<Article> articles;

    public SearchResultWrapper(SearchResult searchResult) {
        this.searchResult = searchResult;
        initArticles();
    }

    private void initArticles() {
        articles = new ArrayList<>();
        List<Doc> docs = searchResult.getResponse().getDocs();

        // Данные статьи
        String headLine,        // Заголовок статьи
                snippet,        // Краткое описание статьи
                webURL,         // URL статьи в интернете
                imageURL,       // URL изображения к статье
                author;         // Автор статьи
        Date publicationDate;   // Дата публикации
        for (Doc doc : docs) {
            headLine = doc.getHeadline().getMain();
            snippet = doc.getSnippet();
            webURL = doc.getWebUrl();
            imageURL = getImageUrl(doc.getMultimedia());
            author = getAuthor(doc.getByline().getOriginal(), doc.getHeadline().getKicker());
            publicationDate = getPublicationDate(doc.getPubDate());

            articles.add(new Article(
                    imageURL,
                    webURL,
                    headLine,
                    snippet,
                    author,
                    publicationDate));
        }
    }

    @Nullable
    private String getAuthor(String original, String kicker) {
        if (original == null && kicker == null)
            return null;
        else if (original == null)
            return kicker;
        else if (kicker == null)
            return original;
        else
            return original;
    }

    private String getImageUrl(List<Multimedium> multimedia) {
        if (multimedia.size() != 0) {
            return ArticleSearchApiInterface.URL_HOST_IMAGE + multimedia.get(0).getUrl(); // URL изображения статьи
        } else {
            return null;
        }
    }

    @NonNull
    private Date getPublicationDate(@NonNull String pubDateString) {
        Date pubDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            pubDate = format.parse(pubDateString.split("T")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pubDate;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
