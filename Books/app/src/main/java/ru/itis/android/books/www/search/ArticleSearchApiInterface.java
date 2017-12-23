package ru.itis.android.books.www.search;

import java.util.Arrays;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.itis.android.books.model.bean.SearchResult;
import rx.Observable;

/**
 * Created by Users on 21.12.2017.
 */

public interface ArticleSearchApiInterface {
    String API_KEY = "80aaca9aaf7d42d7bcf17e2af51815c7"; // ключ для получения для авторизации на ресурсу

    String BASE_URL = "http://api.nytimes.com/";
    String BASE_URL_PATH = "svc/search/v2/articlesearch.json";
    String URL_HOST_IMAGE = "https://cdn1.nyt.com/";

    List<String> necessaryInformation = Arrays.asList("headline", "snippet", "web_url", "multimedia", "pub_date", "byline");

    @GET(BASE_URL_PATH)
    Observable<SearchResult> getArticlesByKeyWord(@Query("fl") List<String> necessaryInformation,
                                                  @Query("fq") String keyWord,
                                                  @Query("api-key") String apiKey);
}
