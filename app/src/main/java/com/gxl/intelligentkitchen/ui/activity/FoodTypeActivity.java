package com.gxl.intelligentkitchen.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.ui.customview.ViewPaperIndicator;
import com.gxl.intelligentkitchen.ui.fragment.FoodTypeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class FoodTypeActivity extends FragmentActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.viewPaperIndicator)
    ViewPaperIndicator viewPaperIndicator;
    @Bind(R.id.content)
    ViewPager content;
    @Bind(R.id.back)
    ImageView back;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private String[] titles = {"家常菜谱", "中华菜系", "各地小吃", "外国菜谱", "烘焙"};
    private int[] lms = {13, 2, 3, 10, 369};

    private List<FoodTypeFragment> mFragmentList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodtype_acitivity_main);
        mFragmentList = new ArrayList<>();
        ButterKnife.bind(this);
        for (int i = 0; i < 5; i++) {
            mFragmentList.add(new FoodTypeFragment("update", lms[i], 1));
        }
        content.setAdapter(new FoodViewPaperAdapter(getSupportFragmentManager(), mFragmentList));
        viewPaperIndicator.setmViewPager(content);
        viewPaperIndicator.setmTitles(titles);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }


    public class FoodViewPaperAdapter extends FragmentPagerAdapter {
        List<FoodTypeFragment> list;

        public FoodViewPaperAdapter(FragmentManager fm, List<FoodTypeFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
