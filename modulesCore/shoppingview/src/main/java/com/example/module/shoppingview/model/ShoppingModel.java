package com.example.module.shoppingview.model;

import com.example.module.shoppingview.R;
import com.example.module.shoppingview.contract.IShoppingContract;
import com.example.module.shoppingview.model.classes.Commodity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingModel implements IShoppingContract.IShoppingModel {

    @Override
    public List<Commodity> getRecyclerViewData() {
        List<Commodity> list = new ArrayList<>();
        list.add(new Commodity("繁峙县山西小米5斤装五谷粗粮黄小米2.5kg", "￥49.00 / 袋", R.drawable.commodity1, "https://www.baidu.com"));
        list.add(new Commodity("兴县2024年新货薏米仁1.8kg/桶装", "￥40.00 / 桶", R.drawable.commodity2, "https://www.baidu.com"));
        list.add(new Commodity("秭归县农家散养土鸡蛋40枚", "￥55.00 / 盒", R.drawable.commodity3, "https://www.baidu.com"));
        list.add(new Commodity("繁峙县山西小米5斤装五谷粗粮黄小米2.5kg", "￥49.00 / 袋", R.drawable.commodity1, "https://www.baidu.com"));
        list.add(new Commodity("兴县2024年新货薏米仁1.8kg/桶装", "￥40.00 / 桶", R.drawable.commodity2, "https://www.baidu.com"));
        list.add(new Commodity("秭归县农家散养土鸡蛋40枚", "￥55.00 / 盒", R.drawable.commodity3, "https://www.baidu.com"));
        return list;
    }
}
