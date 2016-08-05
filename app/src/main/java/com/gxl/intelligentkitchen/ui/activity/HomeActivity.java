package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.model.FoodModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;
import com.gxl.intelligentkitchen.ui.fragment.FoodFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        fragmentmanager = getSupportFragmentManager();
        fragmenttransaction = fragmentmanager.beginTransaction();
        mFoodFragment=new FoodFragment();
        fragmenttransaction.replace(R.id.content,mFoodFragment);
        fragmenttransaction.commit();
    }

    @OnClick({R.id.foodPage, R.id.controlPage, R.id.personPage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.foodPage:
                clearView();
                foodIv.setImageResource(R.drawable.main_recipe_red);
                foodTv.setTextColor(Color.parseColor("#FD7575"));
                break;
            case R.id.controlPage:
                clearView();
                controlIv.setImageResource(R.drawable.main_home_red);
                controlTv.setTextColor(Color.parseColor("#FD7575"));
                break;
            case R.id.personPage:
                clearView();
                personIv.setImageResource(R.drawable.main_user_red);
                personTv.setTextColor(Color.parseColor("#FD7575"));
                break;
        }
    }
        public void clearView() {
            foodIv.setImageResource(R.drawable.main_recipe_gray);
            foodTv.setTextColor(Color.parseColor("#828383"));
            controlIv.setImageResource(R.drawable.main_home_gray);
            controlTv.setTextColor(Color.parseColor("#828383"));
            personIv.setImageResource(R.drawable.main_user_gray);
            personTv.setTextColor(Color.parseColor("#828383"));
        }
}
