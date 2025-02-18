package com.example.module.shoppingview.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shoppingview.model.classes.Commodity;

import java.util.List;

public class ShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ShoppingCartViewHolder> {

    private List<Commodity> commodityList;

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    public ShoppingCartRecyclerViewAdapter(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ShoppingCartRecyclerViewAdapter(List<Commodity> commodityList, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.commodityList = commodityList;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ShoppingCartRecyclerViewAdapter.ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartRecyclerViewAdapter.ShoppingCartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
