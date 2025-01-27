package com.example.personalinfoview.presenter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.R;
import com.example.personalinfoview.bean.MenuItem;
import com.example.personalinfoview.view.PersonalInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfoPresenter implements IInfoContract.Presenter {

    private final PersonalInfoFragment view;

    public PersonalInfoPresenter(PersonalInfoFragment view) {
        this.view = view;
    }

    @Override
    public void loadMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(R.drawable.ic_info, "个人信息"));
        items.add(new MenuItem(R.drawable.ic_star, "我的收藏"));
        items.add(new MenuItem(R.drawable.ic_mail, "我的消息"));
        items.add(new MenuItem(R.drawable.ic_setup, "设置"));
        view.showMenuItems(items);
    }

    @Override
    public void onMenuItemClick(int position) {
        // 根据业务逻辑处理点击事件
        switch (position) {
            case 0:
                ARouter.getInstance()
                        .build("/personalinfoview/InfoActivity")
                        .navigation();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
