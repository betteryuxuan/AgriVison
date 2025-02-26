package com.example.module.shoppingview.presenter;

import com.example.module.shoppingview.contract.IShoppingContract;
import com.example.module.shoppingview.model.classes.Commodity;

import java.util.List;

public class ShoppingPresenter implements IShoppingContract.IShoppingPresenter{

    private IShoppingContract.IShoppingView mShoppingView;

    private IShoppingContract.IShoppingModel mShoppingModel;

    public ShoppingPresenter(IShoppingContract.IShoppingView mShoppingView, IShoppingContract.IShoppingModel mShoppingModel) {
        this.mShoppingView = mShoppingView;
        this.mShoppingModel = mShoppingModel;
    }

    @Override
    public void loadRecyclerViewData() {

        List<Commodity> list = mShoppingModel.getRecyclerViewData();

        mShoppingView.setupRecyclerView(list);
    }
}
