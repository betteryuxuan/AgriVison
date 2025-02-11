package com.example.personalinfoview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.libBase.bean.AnimationUtil;
import com.example.personalinfoview.R;
import com.example.personalinfoview.bean.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<MenuItem> items;
    private OnItemClickListener listener;

    public MenuAdapter(List<MenuItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MenuItem item = items.get(position);
        if (item.getImageResId() != 0) {
            holder.img.setImageResource(item.getImageResId());
        }
        holder.tv.setText(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.menuIcon);
            tv = itemView.findViewById(R.id.menuTitle);

        }
    }
}
