package com.example.agrivison;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.login.room.UserDataBase;
import com.github.boybeak.skbglobal.SoftKeyboardGlobal;

public class App extends Application {

    private UserDataBase userDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        SoftKeyboardGlobal.INSTANCE.install(this, false);

    }

}
