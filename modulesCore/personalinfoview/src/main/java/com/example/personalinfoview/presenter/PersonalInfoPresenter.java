package com.example.personalinfoview.presenter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
                        .build("/personalinfoview/MyFavoritesActivity")
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
                    logout();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(mView.getContext());
                    View customView = inflater.inflate(R.layout.dialog_logout_layout, null);
                    AlertDialog dialog = new AlertDialog.Builder(mView.getContext())
                            .setView(customView)
                            .create();
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
                    Button btnConfirm = customView.findViewById(R.id.btn_logout_confirm);
                    Button btnCancel = customView.findViewById(R.id.btn_logout_cancel);

                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logout();
                            dialog.dismiss();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            default:
                break;
        }
    }

    private void logout() {
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
    public String getUserAvatar() {
        return mModel.getUserAvatar();
    }

    public void updateUserInfo(User user) {
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.UpdateUserInfo(user);
            }
        });
    }


}
