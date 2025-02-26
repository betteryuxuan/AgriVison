package com.example.module.shoppingview.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shoppingview.R;
import com.example.module.shoppingview.contract.IShoppingContract;
import com.example.module.shoppingview.custom.ShoppingCartFragment;
import com.example.module.shoppingview.model.classes.Commodity;
import com.example.module.shoppingview.view.adapter.CommoditiesRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ShoppingFragment extends Fragment implements IShoppingContract.IShoppingView{

    private RecyclerView recyclerView;
    private IShoppingContract.IShoppingPresenter presenter;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_shopping_commodities);
        fab = view.findViewById(R.id.fab_shopping_cart);

        initView();
        initListener();
    }

    @Override
    public void setPresenter(IShoppingContract.IShoppingPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView() {

        presenter.loadRecyclerViewData();

    }

    @Override
    public void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartFragment shoppingCartFragment = ShoppingCartFragment.newInstance("购物车", "");
                shoppingCartFragment.show(getChildFragmentManager(), "shoppingCartFragment");
            }
        });
    }

    @Override
    public void setupRecyclerView(List<Commodity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CommoditiesRecyclerViewAdapter(list, new CommoditiesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Commodity commodity) {
                String url = commodity.getUrl();
                Intent intent = new Intent(getContext(), CommodityActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }));
        recyclerView.setNestedScrollingEnabled(false);

    }
}
