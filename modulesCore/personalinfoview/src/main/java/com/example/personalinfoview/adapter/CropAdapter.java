package com.example.personalinfoview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.module.libBase.bean.Crop;
import com.example.personalinfoview.R;

import java.util.ArrayList;
import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private List<Crop> cropList;
    private List<String> colors;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Crop crop);
    }

    public CropAdapter(List<Crop> cropList, OnItemClickListener clickListener) {
        this.cropList = cropList;
        this.clickListener = clickListener;
    }

    public CropAdapter(List<Crop> cornList) {
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
    public CropAdapter.CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cropcard_personal_item, parent, false);
        CropAdapter.CropViewHolder viewHolder = new CropAdapter.CropViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CropAdapter.CropViewHolder holder, int position) {
//        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
//        params.height = (int) (Math.random() * 500 + 400); // 400-900dp随机高度
//        holder.itemView.setLayoutParams(params);

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
            textView = itemView.findViewById(R.id.tv_personal_cropname);
            imageView = itemView.findViewById(R.id.iv_personal_cropimage);
            constraintLayout = itemView.findViewById(R.id.cl_personal_cropcard);
        }
    }
}
