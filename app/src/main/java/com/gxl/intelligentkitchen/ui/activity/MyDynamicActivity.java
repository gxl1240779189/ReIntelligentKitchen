package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.DynamicModel;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.adapter.DynamicAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：我的发布
 */
public class MyDynamicActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listview)
    ListView listview;

    private UserModel mUserModel = new UserModel();
    private DynamicModel mDynamicModel = new DynamicModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodlove_activity_main);
        ButterKnife.bind(this);
        title.setText("我的发布");
        Log.i("TAG", "getSuccess: ");
        Log.i("TAG", "getSuccess: "+mUserModel.getUserLocal().getObjectId());
        mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                mDynamicModel.getDynamicItemByPhone(user, new FoodModelImpl.BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        List<DynamicItem> list = (List<DynamicItem>) o;
                        listview.setAdapter(new DynamicAdapter(MyDynamicActivity.this, R.layout.dynamic_listviewother_item, list));
                    }

                    @Override
                    public void getFailure() {

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
