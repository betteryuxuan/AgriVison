package com.example.module.shoppingview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shoppingview.R;
import com.example.module.shoppingview.model.classes.Commodity;

import java.util.List;

public class CommoditiesRecyclerViewAdapter extends RecyclerView.Adapter<CommoditiesRecyclerViewAdapter.CommoditiesViewHolder> {

    List<Commodity> mCommodities;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Commodity commodity);
    }


    public CommoditiesRecyclerViewAdapter(List<Commodity> mCommodities, OnItemClickListener onItemClickListener) {
        this.mCommodities = mCommodities;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CommoditiesRecyclerViewAdapter.CommoditiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_item, parent, false);
        return new CommoditiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommoditiesRecyclerViewAdapter.CommoditiesViewHolder holder, int position) {
        Commodity commodity = mCommodities.get(position);
        holder.image.setImageResource(commodity.getCommodityImage());
        holder.name.setText(commodity.getCommodityName());
        holder.price.setText(commodity.getCommodityPrice());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(commodity);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCommodities == null ? 0 : mCommodities.size();
    }

    public class CommoditiesViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name, price, num;
        private CardView cardView;

        public CommoditiesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_shopping_pic);
            name = itemView.findViewById(R.id.tv_shopping_name);
            price = itemView.findViewById(R.id.tv_shopping_price);
            cardView = itemView.findViewById(R.id.cv_shopping_view);
        }
    }
}
