package com.example.module.shoppingview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.shoppingview.R;
import com.example.module.shoppingview.model.ShoppingModel;
import com.example.module.shoppingview.presenter.ShoppingPresenter;

@Route(path = "/shoppingview/ShoppingFragment")
public class BaseShoppingFragment extends Fragment {

    private ShoppingFragment shoppingFragment;
    private ShoppingPresenter shoppingPresenter;
    private ShoppingModel shoppingModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingFragment = new ShoppingFragment();
        shoppingModel = new ShoppingModel();
        shoppingPresenter = new ShoppingPresenter(shoppingFragment, shoppingModel);
        shoppingFragment.setPresenter(shoppingPresenter);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shoppingFragment)
                .commit();
    }
}
