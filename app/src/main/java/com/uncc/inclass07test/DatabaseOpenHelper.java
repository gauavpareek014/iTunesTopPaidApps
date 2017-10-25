package com.uncc.inclass07test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gaurav on 10/23/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{
    static final String DB_NAME="filters.db";
    static final int DB_VERSION=1;


    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Filter_Table.onCreate(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Filter_Table.onUpgrade(sqLiteDatabase,i,i1);

    }
}
