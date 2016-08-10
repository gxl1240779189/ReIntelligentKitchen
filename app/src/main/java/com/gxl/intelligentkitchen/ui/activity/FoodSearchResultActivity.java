package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.entity.FoodSearchJson;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.maxwin.view.XListView;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：搜索美食的结果
 */
public class FoodSearchResultActivity extends Activity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.back)
    ImageView back;
    private List<FoodGeneralItem> mDatas;
    private final String SEARCHRESULT = "searchresult";
    private final String FOODITEM = "fooditem";
    private List<FoodSearchJson.foodItem> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodsearchresult_activity_main);
        ButterKnife.bind(this);
        title.setText("搜索结果");
        mDatas = new ArrayList<>();
        FoodSearchJson json = (FoodSearchJson) getIntent().getSerializableExtra(SEARCHRESULT);
        mData = json.getResult().getData();
        for (FoodSearchJson.foodItem item : mData
                ) {
            FoodGeneralItem mFoodGeneralItem = new FoodGeneralItem();
            mFoodGeneralItem.setTitle(item.getTitle());
            mFoodGeneralItem.setImgLink(item.getAlbums().get(0));
            mFoodGeneralItem.setDate(item.getTags());
            mFoodGeneralItem.setTaste(item.getImtro());
            mDatas.add(mFoodGeneralItem);
        }
        listview.setAdapter(new FoodGeneralAdapter(FoodSearchResultActivity.this, R.layout.generalother_listview_item, mDatas));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(FoodSearchResultActivity.this, FoodSearchTeachActivity.class);
                Bundle bundle = new Bundle();
                FoodDetailTeachItem item = change(mData.get(position));
                bundle.putSerializable(FOODITEM, item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public FoodDetailTeachItem change(FoodSearchJson.foodItem jsonItem) {
        FoodDetailTeachItem foodDetailTeachItem = new FoodDetailTeachItem();
        List<FoodAccessories> mAccessoriesList = new ArrayList<>();
        List<FoodTeachStep> mTeachStep = new ArrayList<>();
        String[] main = jsonItem.getIngredients().split(";");
        for (int i = 0; i < main.length; i++) {
            String[] maindetail = main[i].split(",");
            FoodAccessories item = new FoodAccessories();
            item.setName(maindetail[0]);
            item.setNumber(maindetail[1]);
            mAccessoriesList.add(item);
        }
        String[] accessories = jsonItem.getBurden().split(";");
        for (int i = 0; i < accessories.length; i++) {
            String[] accessoriesdetail = accessories[i].split(",");
            FoodAccessories item = new FoodAccessories();
            item.setName(accessoriesdetail[0]);
            item.setNumber(accessoriesdetail[1]);
            mAccessoriesList.add(item);
        }

        List<FoodSearchJson.Steps> stepList = jsonItem.getSteps();
        int num = 1;
        for (FoodSearchJson.Steps item : stepList
                ) {
            FoodTeachStep step = new FoodTeachStep();
            step.setNum(String.valueOf(num));
            step.setTeachtext(item.getStep());
            step.setImagelink(item.getImg());
            mTeachStep.add(step);
            num++;
        }
        foodDetailTeachItem.setStepList(mTeachStep);
        foodDetailTeachItem.setAccessoriesList(mAccessoriesList);
        foodDetailTeachItem.setFoodIntroduction(jsonItem.getImtro());
        foodDetailTeachItem.setFoodImage(jsonItem.getAlbums().get(0));
        foodDetailTeachItem.setFoodTitle(jsonItem.getTitle());
        return foodDetailTeachItem;
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
