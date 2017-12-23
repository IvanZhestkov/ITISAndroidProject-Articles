package ru.itis.android.books.view.activity.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.itis.android.books.R;

/**
 * Created by Admin on 11.12.2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view1)
    public ImageView image;
    @BindView(R.id.headline_text_view)
    public TextView headLineTextView;
    @BindView(R.id.snippet_text_view)
    public TextView snippetTextView;
    @BindView(R.id.author_text_view)
    public TextView authorTextView;
    @BindView(R.id.date_text_view)
    public TextView dateTextView;


    public BookViewHolder(View itemView, final OnArticleClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(getAdapterPosition());
            }
        });
    }
}