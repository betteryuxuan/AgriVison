package com.example.personalinfoview.presenter;

import android.net.Uri;

import com.example.personalinfoview.contract.IMyInfoContract;
import com.example.personalinfoview.model.MyInfoModel;
import com.example.personalinfoview.view.MyInfoActivity;

public class MyInfoPresenter implements IMyInfoContract.Presenter {

    private final MyInfoActivity mView;
    private final MyInfoModel mModel;

    public MyInfoPresenter(MyInfoActivity view) {
        mView = view;
        mModel = new MyInfoModel(this, mView);
    }

    @Override
    public void modifyInfo(String username, String email) {
        mModel.modifyInfo(username, email);
    }

    @Override
    public void saveUserAvatar(Uri avatarUri) {
        mModel.saveUserAvatar(avatarUri.toString());
    }

    @Override
    public String getUserAvatar() {
        return mModel.getUserAvatar();
    }

    public void onModifyInfoSuccess(String username) {

        mView.onModifyInfoSuccess(username);
    }

    public void onModifyInfoFailure() {
        mView.onModifyInfoFailure();
    }

}
