package com.example.module.homepageview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.module.homepageview.R;
import com.example.module.libBase.bean.Crop;

import java.util.List;

public class CategoryDetailsRecyclerViewAdapter extends RecyclerView.Adapter<CategoryDetailsRecyclerViewAdapter.CategoryDetailsRecyclerViewHolder> {
    List<Crop.CropDetail> list;
    private CropCategoryRecyclerViewAdapter.OnItemClickListener clickListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Crop.CropDetail crop);
    }

    public CategoryDetailsRecyclerViewAdapter(List<Crop.CropDetail> list) {
        this.list = list;
    }

    public CategoryDetailsRecyclerViewAdapter(List<Crop.CropDetail> list, CropCategoryRecyclerViewAdapter.OnItemClickListener clickListener, Context context) {
        this.list = list;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryDetailsRecyclerViewAdapter.CategoryDetailsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycard_item, parent, false);
        CategoryDetailsRecyclerViewHolder viewHolder = new CategoryDetailsRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailsRecyclerViewAdapter.CategoryDetailsRecyclerViewHolder holder, int position) {
        Crop.CropDetail crop = list.get(position);
        holder.name.setText(crop.getName());
        Glide.with(context)
                .load(crop.getIcon())  // news.getImage() 返回的是图片的 URL 或文件路径
                .into(holder.image); // 将图片加载到 ImageView
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(crop);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class CategoryDetailsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private ConstraintLayout constraintLayout;
        public CategoryDetailsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_category_image);
            name = itemView.findViewById(R.id.tv_category_name);
            constraintLayout = itemView.findViewById(R.id.cl_category_cropcard);
        }
    }
}
