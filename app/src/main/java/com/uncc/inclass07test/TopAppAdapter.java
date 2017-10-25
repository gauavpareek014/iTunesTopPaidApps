package com.uncc.inclass07test;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gaurav on 10/8/2017.
 */

public class TopAppAdapter extends ArrayAdapter<AppData> {
    List<AppData> mData;
    Context mContext;
    int mResource;
    public TopAppAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<AppData> objects) {
        super(context, resource, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        AppData appData = mData.get(position);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView price = (TextView)convertView.findViewById(R.id.price);
        ImageView movieImage = (ImageView)convertView.findViewById(R.id.movieImage);
        ImageView dollarImage = (ImageView)convertView.findViewById(R.id.dollarImage);

        name.setText(appData.getAppName());
        price.setText("Price: USD "+appData.getAppPrice());
        dollarImage.setImageResource(Integer.parseInt(appData.getDollarImage()));
        if(appData.getAppThumb()!=null) {
            Picasso.with(mContext).load(appData.getAppThumb()).into(movieImage);
        }
        return convertView;
    }
}
