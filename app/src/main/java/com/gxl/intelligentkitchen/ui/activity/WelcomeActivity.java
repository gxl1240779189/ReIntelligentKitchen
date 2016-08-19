package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.gxl.intelligentkitchen.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.smssdk.SMSSDK;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：欢迎界面
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity_main);
        //设置BmobConfig：允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey(必填)
                .setApplicationId("6a695ae946f5ebfa7fffa6d5c8ee5a32")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(5500)
                .build();
        Bmob.initialize(config);
        SMSSDK.initSDK(this, "c7ab0de860bc", "2f031be4660cf6d57be19df4ab57cf1f");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                finish();
            }
        }, 2000);
    }
}
