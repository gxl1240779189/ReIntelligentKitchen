package com.gxl.intelligentkitchen.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.gxl.intelligentkitchen.model.FoodLoveModel;
import com.gxl.intelligentkitchen.presenter.FoodTeachActivityPresenter;
import com.gxl.intelligentkitchen.ui.adapter.FoodAccessoriesAdapter;
import com.gxl.intelligentkitchen.ui.adapter.FoodTeachStepAdapter;
import com.gxl.intelligentkitchen.ui.view.IFoodTeachActivity;
import com.gxl.intelligentkitchen.utils.LogUtils;
import com.gxl.intelligentkitchen.utils.NetUtil;
import com.gxl.intelligentkitchen.utils.StringUtils;
import com.gxl.intelligentkitchen.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：展示美食的详细做法
 */
public class FoodTeachActivity extends FragmentActivity implements IFoodTeachActivity {
    private final String FOODITEM = "fooditem";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.favorite)
    ImageView favorite;
    @Bind(R.id.food_image)
    ImageView foodImage;
    @Bind(R.id.food_title)
    TextView foodTitle;
    @Bind(R.id.food_introduction)
    TextView foodIntroduction;
    @Bind(R.id.write_image)
    ImageView writeImage;
    @Bind(R.id.write_name)
    TextView writeName;
    @Bind(R.id.write_date)
    TextView writeDate;
    @Bind(R.id.FoodAccessoriesLv)
    ListView FoodAccessoriesLv;
    @Bind(R.id.FoodTeachStepLv)
    ListView FoodTeachStepLv;
    @Bind(R.id.food_content)
    ScrollView foodContent;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private FoodTeachActivityPresenter mFoodTeachActivityPresenter;
    private String mUrlLink;
    private FoodDetailTeachItem mItem;
    private FoodLoveModel mFoodLoveModel = new FoodLoveModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodteach_activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        mUrlLink = getIntent().getStringExtra("URLLINK");
        imageLoader.init(ImageLoaderConfiguration.createDefault(FoodTeachActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mFoodTeachActivityPresenter = new FoodTeachActivityPresenter(this);
        if (NetUtil.checkNet(FoodTeachActivity.this)) {
            loading.setVisibility(View.VISIBLE);
            foodContent.setVisibility(View.GONE);
            mFoodTeachActivityPresenter.onLoadData(mUrlLink);
        } else {
            foodContent.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back, R.id.favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.favorite:
                if (mItem != null) {
                    if (mFoodLoveModel.onQuery(mItem)) {
                        favorite.setBackgroundResource(R.drawable.star);
                        mFoodLoveModel.onDelete(mItem);
                        ToastUtils.showShort(FoodTeachActivity.this, "取消成功");
                    } else {
                        favorite.setBackgroundResource(R.drawable.gray_star_enabled);
                        mFoodLoveModel.onInsert(mItem);
                        ToastUtils.showShort(FoodTeachActivity.this, "收藏成功");
                    }
                }
                break;
        }
    }

    @Override
    public void onLoadData(FoodDetailTeachItem item) {
        if (mFoodLoveModel.onQuery(item)) {
            favorite.setBackgroundResource(R.drawable.gray_star_enabled);
        }
        mItem = item;
        foodContent.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        imageLoader.displayImage(item.getFoodImage(), foodImage, options);
        foodTitle.setText(item.getFoodTitle());
        foodIntroduction.setText(item.getFoodIntroduction());
        writeName.setText(item.getWriteName());
        imageLoader.displayImage(item.getWritePhoto(), writeImage, options);
        writeDate.setText(item.getWriteDate());
        FoodAccessoriesLv.setAdapter(new FoodAccessoriesAdapter(FoodTeachActivity.this, R.layout.foodaccessories_listview_item, item.getAccessoriesList()));
        FoodTeachStepLv.setAdapter(new FoodTeachStepAdapter(FoodTeachActivity.this, R.layout.foodteachstep_listview_item, item.getStepList()));
    }
}
