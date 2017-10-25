package com.uncc.inclass07test;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by gaurav on 10/23/2017.
 */

public class Filter_Table {
    static final String TABLENAME= "filter";
    static final String COLUMN_ID="id";
    static final String COLUMN_NAME="name";//name,price,thumb_url
    static final String COLUMN_PRICE="price";
    static final String COLUMN_THUMB_URL="thumb_url";
    static final String COLUMN_THUMB_URL_LARGE="thumb_url_large";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME +" (");
        sb.append(COLUMN_ID+" integer primary key autoincrement, ");
        sb.append(COLUMN_NAME+ " text not null, ");
        sb.append(COLUMN_PRICE +" real not null, ");
        sb.append(COLUMN_THUMB_URL +" real not null, ");
        sb.append(COLUMN_THUMB_URL_LARGE+" text not null);");
        Log.d("demo", String.valueOf(sb));

        try{
            db.execSQL(String.valueOf(sb));
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        Filter_Table.onCreate(db);
    }
}
