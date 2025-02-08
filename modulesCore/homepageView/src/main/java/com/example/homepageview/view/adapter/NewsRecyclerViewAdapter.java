package com.example.homepageview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepageview.R;
import com.example.homepageview.model.classes.News;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsHolder> {

    private final String TAG = "NewsRecyclerViewAdapter";
    List<News> newsList;

    public NewsRecyclerViewAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsRecyclerViewAdapter.NewsHolder viewHolder = new NewsHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsHolder holder, int position) {
        News news = newsList.get(position);
        holder.textView.setText(news.getText());
        holder.imageView.setImageResource(news.getImage());
        holder.newsLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.my_anim));
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private ConstraintLayout newsLayout;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_homepage_newsimage);
            textView = itemView.findViewById(R.id.tv_homepage_newstext);
            newsLayout = itemView.findViewById(R.id.cl_homepage_newsList);
        }
    }
}
