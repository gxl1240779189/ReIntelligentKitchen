package com.gxl.intelligentkitchen.ui.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.ui.fragment.FoodTypeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class FoodTypeActivity extends FragmentActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.newfoodTv)
    TextView newfoodTv;
    @Bind(R.id.newfood)
    RelativeLayout newfood;
    @Bind(R.id.hotfoodTv)
    TextView hotfoodTv;
    @Bind(R.id.hotfood)
    RelativeLayout hotfood;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.bashLinearLayout)
    LinearLayout bashLinearLayout;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private String[] titles = {"家常菜谱", "中华菜系", "各地小吃", "外国菜谱", "烘焙", "我的收藏"};
    private int[]    lms={13,2,3,10,369};
    private int mPosite;
    private FoodTypeFragment mNewFoodFragment;
    private FoodTypeFragment mHotFoodFragment;

    private final String UPDATE="update";
    private final String RENQI="renqi";

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodtype_acitivity_main);
        mPosite=getIntent().getIntExtra("position",13);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        title.setText(titles[mPosite]);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mNewFoodFragment=new FoodTypeFragment(UPDATE,lms[mPosite],1);
        mFragmentTransaction.add(R.id.content, mNewFoodFragment);
        mFragmentTransaction.commit();
    }

    @OnClick({R.id.back, R.id.newfood, R.id.hotfood})
    public void onClick(View view) {
        FragmentTransaction fragmenttransaction = mFragmentManager
                .beginTransaction();
        hideFragments(fragmenttransaction);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.newfood:
                clearView();
                newfood.setBackgroundResource(R.drawable.btn_gray_big_pressed);
                newfoodTv.setTextColor(Color.parseColor("#ffffff"));
                if (mNewFoodFragment != null) {
                    fragmenttransaction.show(mNewFoodFragment);
                }
                break;
            case R.id.hotfood:
                clearView();
                hotfood.setBackgroundResource(R.drawable.btn_gray_big_pressed);
                if (mHotFoodFragment != null) {
                    fragmenttransaction.show(mHotFoodFragment);
                } else {
                    mHotFoodFragment = new FoodTypeFragment(RENQI,lms[mPosite],1);
                    fragmenttransaction.add(R.id.content, mHotFoodFragment);
                }
                hotfoodTv.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
        fragmenttransaction.commit();
    }

    private void clearView() {
        newfood.setBackgroundResource(R.drawable.btn_gray_big_normal);
        newfoodTv.setTextColor(Color.parseColor("#82858b"));
        hotfood.setBackgroundResource(R.drawable.btn_gray_big_normal);
        hotfoodTv.setTextColor(Color.parseColor("#82858b"));
    }

    private void hideFragments(
            android.support.v4.app.FragmentTransaction fragment) {
        if (mNewFoodFragment != null) {
            fragment.hide(mNewFoodFragment);
        }
        if (mHotFoodFragment != null) {
            fragment.hide(mHotFoodFragment);
        }
    }
}
