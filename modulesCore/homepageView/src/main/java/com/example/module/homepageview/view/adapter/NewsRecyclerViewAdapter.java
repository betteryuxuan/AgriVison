package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.News;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsHolder> {

    private final String TAG = "NewsRecyclerViewAdapter";
    List<News.Item> newsList;
    private OnItemClickListener clickListener;
    private Context mContext;

    public NewsRecyclerViewAdapter(List<News.Item> newsList, OnItemClickListener clickListener, Context mContext) {
        this.newsList = newsList;
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(News.Item news);
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
        News.Item news = newsList.get(position);
        holder.newsLayout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.my_anim));
        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(news);
                }
            }
        });
        Glide.with(mContext)
                .load(news.getImage())  // news.getImage() 返回的是图片的 URL 或文件路径
                .into(holder.imageView); // 将图片加载到 ImageView
        holder.textView.setText(news.getTitle());
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
