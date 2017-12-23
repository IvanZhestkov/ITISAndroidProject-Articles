package ru.itis.android.books.view.activity.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.itis.android.books.R;
import ru.itis.android.books.model.bean.Article;

/**
 * Created by Admin on 11.12.2017.
 */

public class Adapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<Article> articles;

    private OnArticleClickListener listener;

    private Context context;

    public Adapter(OnArticleClickListener listener, Context context) {
        articles = new ArrayList<>();
        this.listener = listener;
        this.context = context;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Article item = articles.get(position);

        holder.headLineTextView.setText(item.getHeadLine());
        holder.snippetTextView.setText(item.getSnippet());
        holder.authorTextView.setText(item.getAuthor());
        holder.dateTextView.setText(new SimpleDateFormat("dd.MM.yyyy").format(item.getPublicationDate()));

        if(item.getImageURL() != null) {
            Glide.with(context)
                    .load(item.getImageURL())
                    .transition(DrawableTransitionOptions.withCrossFade(5000))
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
