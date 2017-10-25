package com.uncc.inclass07test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gaurav on 10/24/2017.
 */

public class BottomAppAdapter extends RecyclerView.Adapter<BottomAppAdapter.ViewHolder> {
    ArrayList<Filter> mData;
    static Context mContext;
    static DatabaseDataManager dm;

    public BottomAppAdapter(ArrayList<Filter> mData,Context context) {
        this.mData = mData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Filter filter = mData.get(position);
        holder.name.setText(filter.name);
        holder.price.setText("Price: USD "+filter.price.toString());
        if(filter.thumb_url_large!=null) {
            Picasso.with(mContext).load(filter.thumb_url_large).into(holder.appImage);
        }
        if(filter.price>=0 && filter.price<=1.99){
            holder.rating.setImageResource(R.drawable.price_low);
        }else if(filter.price>=2.00 && filter.price<=5.99){
            holder.rating.setImageResource(R.drawable.price_medium);
        }else if(filter.price>=6.00){
            holder.rating.setImageResource(R.drawable.price_high);
        }
       // holder.appImage.setText(a.summary);
        holder.filter = filter;
        holder.delete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView appImage,delete,rating;
        Filter filter;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            appImage = (ImageView)itemView.findViewById(R.id.appImage);
            delete = (ImageView)itemView.findViewById(R.id.delete);
            rating = (ImageView)itemView.findViewById(R.id.rating);
            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dm = new DatabaseDataManager(mContext);
                    dm.deleteFilter(filter);
                    notifyDataSetChanged();


                }
            });*/
        }
    }

}