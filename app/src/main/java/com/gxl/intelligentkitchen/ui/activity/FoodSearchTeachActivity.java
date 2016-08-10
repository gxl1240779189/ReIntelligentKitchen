package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.model.FoodLoveModel;
import com.gxl.intelligentkitchen.ui.adapter.FoodAccessoriesAdapter;
import com.gxl.intelligentkitchen.ui.adapter.FoodTeachStepAdapter;
import com.gxl.intelligentkitchen.ui.customview.FixedListView;
import com.gxl.intelligentkitchen.ui.customview.RoundImageView;
import com.gxl.intelligentkitchen.utils.LogUtils;
import com.gxl.intelligentkitchen.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：搜索美食教学页面
 */
public class FoodSearchTeachActivity extends Activity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.favorite)
    ImageView favorite;
    @Bind(R.id.food_image)
    ImageView foodImage;
    @Bind(R.id.food_title)
    TextView foodTitle;
    @Bind(R.id.food_introduction)
    TextView foodIntroduction;
    @Bind(R.id.FoodAccessoriesLv)
    FixedListView FoodAccessoriesLv;
    @Bind(R.id.FoodTeachStepLv)
    FixedListView FoodTeachStepLv;

    private final String FOODITEM = "fooditem";
    @Bind(R.id.write_image)
    RoundImageView writeImage;
    @Bind(R.id.write_name)
    TextView writeName;
    @Bind(R.id.write_date)
    TextView writeDate;
    @Bind(R.id.food_content)
    ScrollView foodContent;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;
    private FoodDetailTeachItem mFoodItem;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private FoodLoveModel mFoodLoveModel = new FoodLoveModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodsearchteach_activity_main);
        mFoodItem = (FoodDetailTeachItem) getIntent().getSerializableExtra(FOODITEM);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        if (mFoodLoveModel.onQuery(mFoodItem)) {
            favorite.setBackgroundResource(R.drawable.gray_star_enabled);
        }
        imageLoader.init(ImageLoaderConfiguration.createDefault(FoodSearchTeachActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        imageLoader.displayImage(mFoodItem.getFoodImage(), foodImage, options);
        foodTitle.setText(mFoodItem.getFoodTitle());
        foodIntroduction.setText(mFoodItem.getFoodIntroduction());
        LogUtils.i("TAG",mFoodItem.getAccessoriesList().toString());
        if (mFoodItem.getWriteName() != null && !TextUtils.isEmpty(mFoodItem.getWriteName())) {
            writeName.setText(mFoodItem.getWriteName());
        } else {
            writeName.setVisibility(View.GONE);
        }
        if (mFoodItem.getWriteDate() != null && !TextUtils.isEmpty(mFoodItem.getWriteDate())) {
            writeDate.setText(mFoodItem.getWriteDate());
        } else {
            writeDate.setVisibility(View.GONE);
        }

        if (mFoodItem.getWritePhoto() != null && !TextUtils.isEmpty(mFoodItem.getWritePhoto())) {
            imageLoader.displayImage(mFoodItem.getWritePhoto(), writeImage, options);
        } else {
            writeImage.setVisibility(View.GONE);
        }
        FoodAccessoriesLv.setAdapter(new FoodAccessoriesAdapter(FoodSearchTeachActivity.this, R.layout.foodaccessories_listview_item, mFoodItem.getAccessoriesList()));
        FoodTeachStepLv.setAdapter(new FoodTeachStepAdapter(FoodSearchTeachActivity.this, R.layout.foodteachstep_listview_item, mFoodItem.getStepList()));
    }

    @OnClick({R.id.back, R.id.favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.favorite:
                if (mFoodItem != null) {
                    if (mFoodLoveModel.onQuery(mFoodItem)) {
                        favorite.setBackgroundResource(R.drawable.star);
                        mFoodLoveModel.onDelete(mFoodItem);
                        ToastUtils.showShort(FoodSearchTeachActivity.this, "取消成功");
                    } else {
                        favorite.setBackgroundResource(R.drawable.gray_star_enabled);
                        mFoodLoveModel.onInsert(mFoodItem);
                        ToastUtils.showShort(FoodSearchTeachActivity.this, "收藏成功");
                    }
                }
                break;
        }
    }
}
