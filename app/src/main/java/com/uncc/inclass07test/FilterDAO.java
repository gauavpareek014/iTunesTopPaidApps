package com.uncc.inclass07test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/23/2017.
 */

public class FilterDAO {
    private SQLiteDatabase db;

    public FilterDAO(SQLiteDatabase db){
        this.db=db;
    }

    public long save(Filter filter)
    {
        ContentValues values=new ContentValues();
        values.put(Filter_Table.COLUMN_NAME,filter.getName());
        values.put(Filter_Table.COLUMN_PRICE,filter.getPrice());
        values.put(Filter_Table.COLUMN_THUMB_URL,filter.getThumb_url());
        values.put(Filter_Table.COLUMN_THUMB_URL_LARGE,filter.getThumb_url_large());
        return db.insert(Filter_Table.TABLENAME,null,values);

    }
    public boolean update(Filter filter)
    {
        ContentValues values=new ContentValues();
        values.put(Filter_Table.COLUMN_NAME,filter.getName());
        values.put(Filter_Table.COLUMN_PRICE,filter.getPrice());
        values.put(Filter_Table.COLUMN_THUMB_URL,filter.getThumb_url());
        values.put(Filter_Table.COLUMN_THUMB_URL_LARGE,filter.getThumb_url_large());

        return db.update(Filter_Table.TABLENAME,values,Filter_Table.COLUMN_ID + "=?", new String[]{filter.getId()+""}) > 0;


    }

    public boolean delete(Filter filter)
    {
        return db.delete(Filter_Table.TABLENAME,Filter_Table.COLUMN_ID + "=?",new String[]{filter.getId()+""}) > 0;

    }

    public Filter get(long id)
    {
        Filter filter =null;

        Cursor c=db.query(true,Filter_Table.TABLENAME,new String[]{Filter_Table.COLUMN_ID,Filter_Table.COLUMN_NAME,Filter_Table.COLUMN_PRICE,Filter_Table.COLUMN_THUMB_URL,Filter_Table.COLUMN_THUMB_URL_LARGE}
                ,Filter_Table.COLUMN_ID + "=?",new String[]{id + ""},null,null,null,null,null);

        if(c!=null &&c.moveToFirst()){

            filter=buildFilterFromCursor(c);
            if(!c.isClosed())
                c.close();
        }
        return filter;
    }



    private Filter buildFilterFromCursor(Cursor c){
        Filter filter=null;
        if(c !=null){
            filter=new Filter();
            filter.setId(c.getLong(0));
            filter.setName(c.getString(1));
            filter.setPrice(c.getDouble(2));
            filter.setThumb_url(c.getString(3));
            filter.setThumb_url_large(c.getString(4));
        }
        return filter;
    }



    public List<Filter> getAll()
    {
        List<Filter> filters=new ArrayList<Filter>();

        Cursor c=db.query(Filter_Table.TABLENAME,new String[]{Filter_Table.COLUMN_ID,Filter_Table.COLUMN_NAME,Filter_Table.COLUMN_PRICE,Filter_Table.COLUMN_THUMB_URL,Filter_Table.COLUMN_THUMB_URL_LARGE},
                null,null,null,null,null);
        Log.d("demo", String.valueOf(c));

        if(c!=null && c.moveToFirst()){

            do {
                Filter filter=buildFilterFromCursor(c);
                if (filter!=null)
                {
                    filters.add(filter);
                }
            } while (c.moveToNext());

            if(!c.isClosed()) {
                c.close();
            }
        }
        return filters;
    }

}

