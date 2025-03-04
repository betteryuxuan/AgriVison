package com.example.personalinfoview.presenter;

import android.content.DialogInterface;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.R;
import com.example.personalinfoview.bean.MenuItem;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.model.PersonalInfoModel;
import com.example.personalinfoview.view.PersonalInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfoPresenter implements IInfoContract.Presenter {

    private final PersonalInfoFragment mView;
    private final PersonalInfoModel mModel;

    public PersonalInfoPresenter(PersonalInfoFragment view) {
        mView = view;
        mModel = new PersonalInfoModel(this, mView.getContext());
    }

    @Override
    public void loadMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(R.drawable.ic_info, "个人信息"));
        items.add(new MenuItem(R.drawable.ic_star, "我的收藏"));
        items.add(new MenuItem(R.drawable.ic_shopping, "我的订单"));
        items.add(new MenuItem(R.drawable.ic_mail, "我的消息"));
        items.add(new MenuItem(R.drawable.ic_setup, "退出登录"));
        mView.showMenuItems(items);
    }

    @Override
    public void onMenuItemClick(int position) {
        switch (position) {
            case 0:
                ARouter.getInstance()
                        .build("/personalinfoview/MyInfoActivity")
                        .withSerializable("user", mView.getUser())
                        .navigation();
                break;
            case 1:
                ARouter.getInstance()
                        .build( "/personalinfoview/MyFavoritesActivity")
                        .navigation();
                break;
            case 2:
                Toast.makeText(mView.getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(mView.getContext(), "暂无消息", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                if (mView.getUser() == null) {
                    Toast.makeText(mView.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    Logout();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(mView.getContext())
                            .setTitle("提示")
                            .setMessage("确定要退出登录吗？")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Logout();
                                }
                            })
                            .setPositiveButton("取消", null)
                            .create();
                    dialog.show();
                }
                break;
            default:
                break;
        }
    }

    private void Logout() {
        mModel.Logout();
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.getActivity().finish();
            }
        });
        ARouter.getInstance()
                .build("/login/LoginActivity")
                .navigation();
    }

    @Override
    public void setUserInfo() {
        mModel.getUserInfo();
    }

    @Override
    public void getUser(User user) {
        mView.showUserInfo(user);
    }

    @Override
    public void saveUserAvatar(Uri avatarUri) {
        mModel.saveUserAvatar(avatarUri.toString());
    }

    @Override
    public String getUserAvatar() {
        return mModel.getUserAvatar();
    }

    public void updateUserInfo(User  user) {
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.UpdateUserInfo(user);
            }
        });
    }


}
