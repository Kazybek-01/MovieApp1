package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.Holder> {
    Context context;
    List<Results> resultsList;
    MovieItemClick listener;

    public void setOnMovieItemClickListener(MovieItemClick listener) {
        this.listener = listener;
    }

    public MoviesAdapter(Context context, List<Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
        return new Holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        double voteAverage = resultsList.get(position).getVote_average()*10.0;
//        if (voteAverage>50&& voteAverage<70){
//            holder.progressView.setProgressColorRes(R.color.yellow);
//        }
//        else if(voteAverage<50){
//            holder.progressView.setProgressColorRes(R.color.red);
//        }
//        else {
//            holder.progressView.setProgressColorRes(R.color.green);
//        }
//        holder.progressView.setProgress((int) voteAverage, true);
        Picasso.get().load(Utils.IMAGE_URL + resultsList.get(position).getPoster_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public Holder(@NonNull View itemView, MovieItemClick listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
    interface MovieItemClick{
        void onClick(int position);
    }
}
