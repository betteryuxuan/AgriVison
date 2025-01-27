package com.example.personalinfoview.contract;


import com.example.personalinfoview.bean.MenuItem;

import java.util.List;

public interface IInfoContract {
    interface View {
        void showMenuItems(List<MenuItem> items);
    }

    interface Presenter {
        void loadMenuItems();
        void onMenuItemClick(int position);

    }

}
