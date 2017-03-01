package com.example.rent.dvdapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rent.dvdapplication.model.DvdEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RENT on 2017-02-13.
 */

public class DvdAdapter extends RecyclerView.Adapter<DvdAdapter.ListItemHolder> {


    private List<DvdEntity> list;
    private OnDvdItemClickedListener listener;
    private Context context;

    public DvdAdapter (List<DvdEntity> list, OnDvdItemClickedListener listener,Context context){
        this.list=list;
        this.listener=listener;
        this.context=context;
    }
    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dvd_item_view, parent, false);
        return new ListItemHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ListItemHolder holder, int position) {

        holder.titleTV.setText(list.get(position).getTitle());
        holder.genreTV.setText(list.get(position).getGenre());
        holder.yearTV.setText(list.get(position).getYear()+"");
        int amount = list.get(position).getAmount();
        if(amount==0){
            holder.amountTV.setText("out of stock :(");
        }
        else holder.amountTV.setText(list.get(position).getAmount()+"");
        Picasso.with(context)
                .load(list.get(position).getPhotoUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.entity = list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder {

        OnDvdItemClickedListener listener;
        private ImageView imageView;
        private TextView titleTV;
        private TextView yearTV;
        private TextView genreTV;
        private TextView amountTV;
        DvdEntity entity;



        public ListItemHolder(View v, final OnDvdItemClickedListener listener) {
            super(v);
            this.listener = listener;
            titleTV = (TextView) v.findViewById(R.id.dvd_item_title_tv);
            yearTV = (TextView) v.findViewById(R.id.dvd_item_year_tv);
            genreTV = (TextView) v.findViewById(R.id.dvd_item_genre_tv);
            amountTV = (TextView) v.findViewById(R.id.dvd_item_amount_tv);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDvdItemClicker(entity);
                }
            });

        }
    }

    public interface OnDvdItemClickedListener {

        void onDvdItemClicker(DvdEntity entity);

    }

}
