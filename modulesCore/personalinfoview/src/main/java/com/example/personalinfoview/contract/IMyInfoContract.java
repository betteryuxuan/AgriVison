package com.example.personalinfoview.contract;


import android.net.Uri;

import com.example.module.libBase.bean.User;
import com.example.personalinfoview.bean.MenuItem;

import java.util.List;

public interface IMyInfoContract {
    interface View {
    }

    interface Presenter {
        void modifyInfo(String username, String email);

        void saveUserAvatar(Uri avatarUri);

        String getUserAvatar();
    }

    interface Model {
        User getMyInfo();

        void modifyInfo(String username, String email);

        void saveUserAvatar(String avatarUri);

        String getUserAvatar();
    }

}
