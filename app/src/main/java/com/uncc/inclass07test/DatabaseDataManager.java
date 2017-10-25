package com.uncc.inclass07test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by gaurav on 10/23/2017.
 */

public class DatabaseDataManager {private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    SQLiteDatabase db;

    FilterDAO filterDAO;
    public DatabaseDataManager(Context mContext)
    {
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();

        filterDAO=new FilterDAO(db);

    }
    public void close()
    {
        if (db!=null)
        {
            db.close();
        }
    }

    public FilterDAO getFilterDAO(){
        return this.filterDAO;
    }
    public long saveFilter(Filter filter)
    {
        return this.filterDAO.save(filter);
    }

    public boolean updateFilter(Filter filter)
    {
        return this.filterDAO.update(filter);
    }
    public boolean deleteFilter(Filter filter)
    {
        return this.filterDAO.delete(filter);
    }
    public Filter getFilter(long id)
    {
        return this.filterDAO.get(id);
    }
    public List<Filter> getAllFilters(){
        return this.filterDAO.getAll();
    }
}
