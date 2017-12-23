package ru.itis.android.books.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.itis.android.books.model.database.table.ArticleTable;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "articlesproject.db";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ArticleTable.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
