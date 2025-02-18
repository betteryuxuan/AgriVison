package com.example.module.shoppingview.contract;

import com.example.module.shoppingview.base.BaseView;
import com.example.module.shoppingview.model.classes.Commodity;

import java.util.List;

public interface IShoppingContract {

    interface IShoppingView extends BaseView<IShoppingPresenter> {

        void initView();

        void initListener();

        void setupRecyclerView(List<Commodity> list);
    }

    interface IShoppingPresenter {

        void loadRecyclerViewData();
    }

    interface IShoppingModel {

        List<Commodity> getRecyclerViewData();
    }
}
