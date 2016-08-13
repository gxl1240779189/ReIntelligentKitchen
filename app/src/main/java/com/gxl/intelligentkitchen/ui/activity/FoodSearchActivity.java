package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodSearchJson;
import com.gxl.intelligentkitchen.helper.SharedpreferencesHelper;
import com.gxl.intelligentkitchen.model.FoodSearchModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.adapter.SearchHistoryAdapter;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：搜索美食Activity
 */
public class FoodSearchActivity extends Activity {
    @Bind(R.id.search_back)
    ImageView searchBack;
    @Bind(R.id.search_text)
    EditText searchText;
    @Bind(R.id.search_button)
    ImageView searchButton;
    @Bind(R.id.search_history_lv)
    ListView searchHistoryLv;
    @Bind(R.id.clear_history_btn)
    Button clearHistoryBtn;
    @Bind(R.id.search_zhuangtai)
    ProgressBar searchZhuangtai;
    private SearchHistoryAdapter mAdapter;

    private final String SEARCHRESULT = "searchresult";
    private SharedpreferencesHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.foodsearch_activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        mHelper = new SharedpreferencesHelper(FoodSearchActivity.this);
        List<String> history_arr = mHelper.returnhistroydata();
        if (history_arr.size() > 0) {
            mAdapter = new SearchHistoryAdapter(this, R.layout.searchhistory_listview_item, history_arr);
            searchHistoryLv.setAdapter(mAdapter);
            searchHistoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new FoodSearchModel().onSearchFood(mAdapter.getItem(position), new FoodModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            mHelper.save(searchText.getText().toString());
                            FoodSearchJson item = (FoodSearchJson) o;
                            Intent intent = new Intent(FoodSearchActivity.this, FoodSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(SEARCHRESULT, item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void getFailure() {

                        }
                    });
                }
            });
        } else {
            searchHistoryLv.setVisibility(View.GONE);
            clearHistoryBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.search_back, R.id.search_button, R.id.clear_history_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.search_button:
                if (!TextUtils.isEmpty(searchText.getText().toString())) {
                    new FoodSearchModel().onSearchFood(searchText.getText().toString(), new FoodModelImpl.BaseListener() {
                        @Override
                        public void getSuccess(Object o) {
                            mHelper.save(searchText.getText().toString());
                            FoodSearchJson item = (FoodSearchJson) o;
                            Intent intent = new Intent(FoodSearchActivity.this, FoodSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(SEARCHRESULT, item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void getFailure() {

                        }
                    });
                } else {
                    ToastUtils.showLong(FoodSearchActivity.this, "搜索名不能为空");
                }
                break;
            case R.id.clear_history_btn:
                mHelper.cleanHistory();
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                searchHistoryLv.setVisibility(View.GONE);
                break;
        }
    }
}
