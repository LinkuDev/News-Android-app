package com.example.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.DB.SavedDB;
import com.example.newsapp.Detailed;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.OOP.SavedList;
import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder>{

    Context context;
    ArrayList<SavedList> savedLists;
    private SavedDB savedDB;


    public SavedAdapter(Context context, ArrayList<SavedList> savedLists) {
        this.context = context;
        this.savedLists = savedLists;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        savedDB = new SavedDB(context);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.saveditems,parent,false);
        savedDB = new SavedDB(context);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SavedList a = savedLists.get(position);

        String imageUrl = a.getImageUrl();
        String url = a.getUrl();

        Picasso.with(context).load(imageUrl).into(holder.imageView);

        holder.tvTitle.setText(a.getTittle());
        holder.tvSource.setText(a.getSource());
        holder.tvDate.setText("\u2022"+a.getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detailed.class);
                intent.putExtra("title",a.getTittle());
                intent.putExtra("source",a.getSource());
                intent.putExtra("time",a.getTime());
                intent.putExtra("desc",a.getDesc());
                intent.putExtra("imageUrl",a.getImageUrl());
                intent.putExtra("url",a.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return savedLists == null ? 0 : savedLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvSource,tvDate,delete;
        ImageView imageView;
        CardView cardView;

        public ViewHolder (@NonNull View view){

            super(view);
            tvTitle = itemView.findViewById(R.id.tvTitles);
            tvSource = itemView.findViewById(R.id.tvSources);
            tvDate = itemView.findViewById(R.id.tvDates);
            imageView = itemView.findViewById(R.id.images);
            cardView = itemView.findViewById(R.id.cardViews);
            delete = imageView.findViewById(R.id.delete);
        }
    }
}
