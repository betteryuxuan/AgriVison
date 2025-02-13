package com.example.module.login.debug;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.boybeak.skbglobal.SoftKeyboardGlobal;

public class debugApplication extends Application {
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
