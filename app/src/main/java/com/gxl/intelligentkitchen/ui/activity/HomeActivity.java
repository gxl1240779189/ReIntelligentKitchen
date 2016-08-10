package com.gxl.intelligentkitchen.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.ui.fragment.FoodFragment;

import com.gxl.intelligentkitchen.ui.fragment.UserFragment;
import com.gxl.intelligentkitchen.utils.AppUtils;
import com.gxl.intelligentkitchen.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 */
public class HomeActivity extends FragmentActivity {
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.foodIv)
    ImageView foodIv;
    @Bind(R.id.foodTv)
    TextView foodTv;
    @Bind(R.id.foodPage)
    RelativeLayout foodPage;
    @Bind(R.id.controlIv)
    ImageView controlIv;
    @Bind(R.id.controlTv)
    TextView controlTv;
    @Bind(R.id.controlPage)
    RelativeLayout controlPage;
    @Bind(R.id.personIv)
    ImageView personIv;
    @Bind(R.id.personTv)
    TextView personTv;
    @Bind(R.id.personPage)
    RelativeLayout personPage;

    public FragmentManager fragmentmanager;
    public FragmentTransaction fragmenttransaction;

    private FoodFragment mFoodFragment;
    private UserFragment mUserFragment;


    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    private DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_activity_main);
        ButterKnife.bind(this);
        BmobUpdateAgent.update(this);
        LogUtils.i("TAG", AppUtils.getVersionName(HomeActivity.this));
        init();
    }

    public void init() {
        imageLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        fragmentmanager = getSupportFragmentManager();
        fragmenttransaction = fragmentmanager.beginTransaction();
        mFoodFragment = new FoodFragment();
        fragmenttransaction.replace(R.id.content, mFoodFragment);
        fragmenttransaction.commit();
    }

    @OnClick({R.id.foodPage, R.id.controlPage, R.id.personPage})
    public void onClick(View view) {
        FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
        hideFragments(fragmenttransaction);
        switch (view.getId()) {
            case R.id.foodPage:
                clearView();
                foodIv.setImageResource(R.drawable.main_recipe_red);
                foodTv.setTextColor(Color.parseColor("#FD7575"));
                if (mFoodFragment != null) {
                    fragmenttransaction.show(mFoodFragment);
                }
                break;
            case R.id.controlPage:
                clearView();
                controlIv.setImageResource(R.drawable.main_home_red);
                controlTv.setTextColor(Color.parseColor("#FD7575"));
                startActivity(new Intent(HomeActivity.this, SendDynamicActivity.class));
                break;
            case R.id.personPage:
                clearView();
                personIv.setImageResource(R.drawable.main_user_red);
                personTv.setTextColor(Color.parseColor("#FD7575"));
                if (mUserFragment != null) {
                    fragmenttransaction.show(mUserFragment);
                } else {
                    mUserFragment = new UserFragment();
                    fragmenttransaction.add(R.id.content, mUserFragment);
                }
                break;
        }
        fragmenttransaction.commit();
    }

    public void clearView() {
        foodIv.setImageResource(R.drawable.main_recipe_gray);
        foodTv.setTextColor(Color.parseColor("#828383"));
        controlIv.setImageResource(R.drawable.main_home_gray);
        controlTv.setTextColor(Color.parseColor("#828383"));
        personIv.setImageResource(R.drawable.main_user_gray);
        personTv.setTextColor(Color.parseColor("#828383"));
    }


    private void hideFragments(
            android.support.v4.app.FragmentTransaction fragment) {
        if (mFoodFragment != null) {
            fragment.hide(mFoodFragment);
        }
        if (mUserFragment != null) {
            fragment.hide(mUserFragment);
        }
    }

}
