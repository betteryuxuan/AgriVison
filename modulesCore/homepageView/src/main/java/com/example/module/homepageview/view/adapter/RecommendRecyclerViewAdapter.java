package com.example.module.homepageview.view.adapter;


import static com.example.module.libBase.utils.AnimationUtils.applyClickAnimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Recommend;

import java.util.List;

public class RecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecommendRecyclerViewAdapter.RecommendViewHolder> {

    private List<Recommend> list;
    private Context context;
    private OnItemClickListener clickListener;

    // 定义一个接口来传递点击事件
    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public RecommendRecyclerViewAdapter(List<Recommend> list, Context context, OnItemClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecommendRecyclerViewAdapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item, parent, false);
        RecommendRecyclerViewAdapter.RecommendViewHolder viewHolder = new RecommendViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendRecyclerViewAdapter.RecommendViewHolder holder, int position) {
        Recommend recommend = list.get(position);
        Glide.with(context)
                .load(recommend.getImage())
                .centerCrop()
                .into(holder.image);
        holder.title.setText(recommend.getTitle());
        holder.content.setText(recommend.getContent());
        final int position1 = position;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.OnItemClick(position1);
                }
            }
        });
        applyClickAnimation(holder.itemView); // 绑定动画

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView content;
        private LinearLayout layout;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_recommend_image);
            title = itemView.findViewById(R.id.tv_recommend_title);
            content = itemView.findViewById(R.id.tv_recommend_text);
            layout = itemView.findViewById(R.id.card_layout);
        }
    }
}
