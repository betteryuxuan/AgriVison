package com.example.module.homepageview.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Crop;

import java.util.List;

public class CropCategoryRecyclerViewAdapter extends RecyclerView.Adapter<CropCategoryRecyclerViewAdapter.CropFoodViewHolder> {

    List<Crop> list;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Crop crop);
    }


    public CropCategoryRecyclerViewAdapter(List<Crop> list) {
        this.list = list;
    }

    public CropCategoryRecyclerViewAdapter(List<Crop> list, OnItemClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CropCategoryRecyclerViewAdapter.CropFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cropcard_item, parent, false);
        CropFoodViewHolder viewHolder = new CropFoodViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CropCategoryRecyclerViewAdapter.CropFoodViewHolder holder, int position) {
        Crop crop = list.get(position);
        holder.imageView.setImageResource(crop.getImage());
        holder.textView.setText(crop.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(crop);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class CropFoodViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CardView cardView;
        private ImageView imageView;
        public CropFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_homepage_cropname);
            imageView = itemView.findViewById(R.id.iv_homepage_cropimage);
            cardView = itemView.findViewById(R.id.cv_homepage_cropcard);
        }
    }
}
