package com.example.sudarshan.stringzsfpolling.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sudarshan.stringzsfpolling.R;

/**
 * Created by admin on 3/6/2018.
 */

public class ShortFilmAdapter extends RecyclerView.Adapter<ShortFilmAdapter.ShortFilmViewHolder> {
    private String[] shortFilms;
    private Context context;

    private OnListItemClickListener onListItemClickListener;

    public ShortFilmAdapter(Context context, String[] shortFilms, OnListItemClickListener listener){
        this.context = context;
        this.shortFilms = shortFilms;
        this.onListItemClickListener = listener;
    }
    @NonNull
    @Override
    public ShortFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ShortFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortFilmViewHolder holder, int position) {
        holder.shortFilmTv.setText(shortFilms[position]);
    }

    @Override
    public int getItemCount() {
        return shortFilms.length;
    }
    class ShortFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shortFilmTv;
        ConstraintLayout constraintLayout;
        public ShortFilmViewHolder(View itemView) {
            super(itemView);
            shortFilmTv = itemView.findViewById(R.id.short_film_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onListItemClickListener.onListItemClick(position, (ConstraintLayout)view);
        }
    }
    public interface OnListItemClickListener{
        public void onListItemClick(int position, ConstraintLayout layout);
    }
}
