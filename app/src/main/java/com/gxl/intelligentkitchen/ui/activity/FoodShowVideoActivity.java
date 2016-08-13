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
import com.gxl.intelligentkitchen.entity.FoodTeachVideo;
import com.gxl.intelligentkitchen.model.FoodVideoModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.adapter.FoodVideoAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：展示可以播放的视频文件
 */
public class FoodShowVideoActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    ListView listview;
    private FoodVideoModel mFoodVideoModel = new FoodVideoModel();
    private FoodVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodlove_activity_main);
        ButterKnife.bind(this);
        title.setText("视频教学");
        mFoodVideoModel.getGeneralFoodsVideoItem(new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<FoodTeachVideo> list = (List<FoodTeachVideo>) o;
                Log.i("TAG", "getSuccess: "+list.get(0).getFoodname());
                mAdapter = new FoodVideoAdapter(list);
                listview.setAdapter(mAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(FoodShowVideoActivity.this, FoodTeachVideoActivity.class);
                        FoodTeachVideo item = (FoodTeachVideo) mAdapter.getItem(position);
                        intent.putExtra("URL", item.getFoodvideo().getUrl());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void getFailure() {

            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
