package com.gxl.intelligentkitchen.application;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

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
        sContext=getApplicationContext();
        //设置BmobConfig：允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey(必填)
                .setApplicationId("6a695ae946f5ebfa7fffa6d5c8ee5a32")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(5500)
                .build();
        Bmob.initialize(config);
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
