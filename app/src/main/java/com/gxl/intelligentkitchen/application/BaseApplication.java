package com.gxl.intelligentkitchen.application;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.smssdk.SMSSDK;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：全局Application
 */
public class BaseApplication extends Application {
    public static Context getmContext() {
        return sContext;
    }

    static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
