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
import com.example.module.homepageview.model.classes.Crop;

import java.util.ArrayList;
import java.util.List;

public class CropRecyclerViewAdapter extends RecyclerView.Adapter<CropRecyclerViewAdapter.CropViewHolder> {

    private List<Crop> cropList;
    private List<String> colors;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Crop crop);
    }

    public CropRecyclerViewAdapter(List<Crop> cropList, OnItemClickListener clickListener) {
        this.cropList = cropList;
        this.clickListener = clickListener;
    }

    public CropRecyclerViewAdapter(List<Crop> cornList) {
        this.cropList = cornList;
        colors = new ArrayList<>();
        colors.add("#9EC840");
        colors.add("#C1D14D");
        colors.add("#CADA4F");
        colors.add("#CC9D5D");
        colors.add("#93BF2A");
    }



    @NonNull
    @Override
    public CropRecyclerViewAdapter.CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cropcard_item, parent, false);
        CropRecyclerViewAdapter.CropViewHolder viewHolder = new CropViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CropRecyclerViewAdapter.CropViewHolder holder, int position) {
        Crop crop = cropList.get(position);
        holder.imageView.setImageResource(crop.getImage());
        holder.textView.setText(crop.getName());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(crop);
                }
            }
        });
//        holder.constraintLayout.setBackgroundColor(Color.parseColor(colors.get(position % colors.size())));

    }
    @Override
    public int getItemCount() {
        return cropList == null ? 0 : cropList.size();
    }

    public class CropViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private ConstraintLayout constraintLayout;
        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_homepage_cropname);
            imageView = itemView.findViewById(R.id.iv_homepage_cropimage);
            constraintLayout = itemView.findViewById(R.id.cl_homepage_cropcard);
        }
    }
}
