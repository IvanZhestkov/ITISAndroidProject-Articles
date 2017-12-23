package ru.itis.android.books.model.bean;

import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Users on 21.12.2017.
 */

public class Article {
    private String imageURL;
    private String webURL;
    private String headLine;
    private String snippet;
    private String author;
    private Date publicationDate;

    public Article(String imageURL, String webURL, String headLine, String snippet, String author, Date publicationDate) {
        this.imageURL = imageURL;
        this.webURL = webURL;
        this.headLine = headLine;
        this.snippet = snippet;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Article(@Nullable String imageURL, String webURL, String headLine, String snippet) {
        this.imageURL = imageURL;
        this.webURL = webURL;
        this.headLine = headLine;
        this.snippet = snippet;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(@Nullable String imageURL) {
        this.imageURL = imageURL;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    @Nullable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nullable String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
