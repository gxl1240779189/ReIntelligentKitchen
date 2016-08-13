package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.helper.MyMediaController;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：视频教学activity
 */
public class FoodTeachVideoActivity extends Activity {

    @Bind(R.id.surface_view)
    VideoView surfaceView;
    @Bind(R.id.loading)
    RelativeLayout loading;

    private MyMediaController mMediaController;
    private static final int TIME = 0;
    private static final int BATTERY = 1;
    private String mURL;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    mMediaController.setTime(msg.obj.toString());
                    break;
                case BATTERY:
                    mMediaController.setBattery(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodteachvideo_activity_main);
        ButterKnife.bind(this);
        mURL = getIntent().getStringExtra("URL");
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = FoodTeachVideoActivity.this.getWindow();
        window.setFlags(flag, flag);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        surfaceView.setVideoPath(mURL);//设置播放地址
        surfaceView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);  //视频全屏
        mMediaController = new MyMediaController(this, surfaceView, this);//实例化控制器
        mMediaController.show(2000);//控制器显示5s后自动隐藏
        surfaceView.setMediaController(mMediaController);//绑定控制器
        surfaceView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                loading.setVisibility(View.GONE);
                ToastUtils.showLong(FoodTeachVideoActivity.this, "准备好");
                surfaceView.start();
            }
        });
        surfaceView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtils.showLong(FoodTeachVideoActivity.this, "播放出错");
                finish();
                return false;
            }
        });
        surfaceView.start();
        surfaceView.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);//设置播放画质 中等画质
        surfaceView.requestFocus();//取得焦点
        registerBoradcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //时间读取线程
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String str = sdf.format(new Date());
                    Message msg = new Message();
                    msg.obj = str;
                    msg.what = TIME;
                    mHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        if (surfaceView != null) {
            surfaceView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(batteryBroadcastReceiver);
        } catch (IllegalArgumentException ex) {

        }
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                Message msg = new Message();
                msg.obj = (level * 100) / scale + "";
                msg.what = BATTERY;
                mHandler.sendMessage(msg);
            }
        }
    };

    public void registerBoradcastReceiver() {
        //注册电量广播监听
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);
    }
}
