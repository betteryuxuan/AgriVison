package com.example.module.homepageview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.libBase.bean.Crop;

import java.util.List;

public class CategoryDetailsRecyclerViewAdapter extends RecyclerView.Adapter<CategoryDetailsRecyclerViewAdapter.CategoryDetailsRecyclerViewHolder> {
    List<Crop> list;
    private CropCategoryRecyclerViewAdapter.OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Crop crop);
    }

    public CategoryDetailsRecyclerViewAdapter(List<Crop> list) {
        this.list = list;
    }

    public CategoryDetailsRecyclerViewAdapter(List<Crop> list, CropCategoryRecyclerViewAdapter.OnItemClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
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
        Crop crop = list.get(position);
        holder.image.setImageResource(crop.getImage());
        holder.name.setText(crop.getName());
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
