package com.example.homepageview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepageview.R;
import com.example.homepageview.model.Corn;

import java.util.List;

public class CornRecyclerViewAdapter extends RecyclerView.Adapter<CornRecyclerViewAdapter.CornViewHolder> {

    private List<Corn> cornList;

    public CornRecyclerViewAdapter(List<Corn> cornList) {
        this.cornList = cornList;
    }

    @NonNull
    @Override
    public CornRecyclerViewAdapter.CornViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corncard_item, parent, false);
        CornRecyclerViewAdapter.CornViewHolder viewHolder = new CornViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CornRecyclerViewAdapter.CornViewHolder holder, int position) {
        Corn corn = cornList.get(position);
        holder.imageView.setImageResource(corn.getImage());
        holder.textView.setText(corn.getName());
    }

    @Override
    public int getItemCount() {
        return cornList == null ? 0 : cornList.size();
    }

    public class CornViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        public CornViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_homepage_cornname);
            imageView = itemView.findViewById(R.id.iv_homepage_cornimage);
        }
    }
}
