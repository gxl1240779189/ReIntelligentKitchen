package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.model.FoodLoveModel;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：收藏美食界面
 */
public class FoodLoveActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    ListView listview;
    private FoodLoveModel mFoodLoveModel = new FoodLoveModel();
    private List<FoodDetailTeachItem> mFoodDetailList;
    private final String FOODITEM = "fooditem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodlove_activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        title.setText("我的收藏");
        List<FoodGeneralItem> generalFoodList = new ArrayList<>();
        mFoodDetailList = mFoodLoveModel.onQuery();
        for (FoodDetailTeachItem item : mFoodDetailList
                ) {
            item.setAccessoriesList(item.getFoodAccessoriesFromSQL());
            item.setStepList(item.getTeachStepFromSQL());
            FoodGeneralItem generalItem = new FoodGeneralItem();
            generalItem.setTitle(item.getFoodTitle());
            generalItem.setDate(item.getWriteDate());
            generalItem.setWriter(item.getWriteName());
            generalItem.setImgLink(item.getFoodImage());
            generalItem.setTaste(item.getFoodIntroduction());
            generalFoodList.add(generalItem);
        }
        listview.setAdapter(new FoodGeneralAdapter(FoodLoveActivity.this, R.layout.generalother_listview_item, generalFoodList));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(FoodLoveActivity.this, FoodSearchTeachActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FOODITEM, mFoodDetailList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
