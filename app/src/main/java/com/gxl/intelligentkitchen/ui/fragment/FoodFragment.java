package com.gxl.intelligentkitchen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.presenter.FoodFragmentPresenter;
import com.gxl.intelligentkitchen.ui.activity.FoodTeachActivity;
import com.gxl.intelligentkitchen.ui.activity.FoodTypeActivity;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;
import com.gxl.intelligentkitchen.ui.view.IFoodFragment;
import com.gxl.intelligentkitchen.utils.NetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.maxwin.view.XListView;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class FoodFragment extends Fragment implements IFoodFragment, XListView.IXListViewListener {

    @Bind(R.id.xListView)
    XListView xListView;
    @Bind(R.id.menu)
    ImageView menu;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    private int[] mIconList = {R.drawable.recipe_lable_1_1,
            R.drawable.recipe_lable_1_2, R.drawable.recipe_lable_2_1,
            R.drawable.recipe_lable_2_3, R.drawable.recipe_lable_4_1,
            R.drawable.recipe_lable_5_2};
    private String[] mNameList = {"家常菜谱", "中华菜系", "各地小吃", "外国菜谱", "烘焙", "我的收藏"};
    private FoodGeneralAdapter mAdapter;
    private LinearLayout mFoodFragmentHead;
    private List<Map<String, Object>> mDataList;
    private ArrayList<FoodGeneralItem> mFoodGeneralList;
    private SimpleAdapter mSimpleAdapter;
    private FoodFragmentPresenter mFoodFragmentPresenter;
    private int mPage = 1;

    private GridView mGridview;
    private SlideShowView mSlideshowView;

    private final String  FOODITEM="fooditem";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.foodfragment_main, container, false);
        ButterKnife.bind(this, v);
        mFoodFragmentHead = (LinearLayout) inflater.inflate(R.layout.foodfragment_head, null);
        mGridview = (GridView) mFoodFragmentHead.findViewById(R.id.gridview);
        mSlideshowView = (SlideShowView) mFoodFragmentHead.findViewById(R.id.slideshowView);
        xListView.addHeaderView(mFoodFragmentHead);
        init();
        return v;
    }

    private void init() {
        mFoodGeneralList = new ArrayList<>();
        mAdapter = new FoodGeneralAdapter(getActivity(), mFoodGeneralList);
        xListView.setAdapter(mAdapter);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        mDataList = new ArrayList<Map<String, Object>>();
        initData();
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList,
                R.layout.gridview_item, from, to);
        mGridview.setAdapter(mSimpleAdapter);
        mFoodFragmentPresenter = new FoodFragmentPresenter(this);
        if(NetUtil.checkNet(getActivity())) {
            mFoodFragmentPresenter.onInitSliderShow();
            mFoodFragmentPresenter.onRefresh("update", 13, 1);
        }else{
            Log.i("TAG", "init: "+"meiwang");
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
            xListView.setVisibility(View.GONE);
        }

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                {
                    Intent intent = new Intent(getActivity(),
                            FoodTypeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FoodGeneralItem> mDatas = mAdapter.returnmDatas();
                FoodGeneralItem fooditem = mDatas.get(position - 2);
                Intent intent = new Intent(getActivity(), FoodTeachActivity.class);
                intent.putExtra("URLLINK",fooditem.getLink());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onLoadMore(List<FoodGeneralItem> list) {
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        xListView.stopLoadMore();
    }

    @Override
    public void onRefresh(List<FoodGeneralItem> list) {
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        xListView.setVisibility(View.VISIBLE);
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
        xListView.stopRefresh();
    }

    @Override
    public void onInitSliderShow(List<SlideShowView.SliderShowViewItem> list) {
        mSlideshowView.initUI(getActivity(), list);
    }

    @Override
    public void onRefresh() {
        mFoodFragmentPresenter.onRefresh("update", 13, 1);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        mFoodFragmentPresenter.onLoadMore("update", 13, mPage);
    }

    public void initData() {
        for (int i = 0; i < mIconList.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", mIconList[i]);
            map.put("text", mNameList[i]);
            mDataList.add(map);
        }
    }

    @OnClick({R.id.menu, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                break;
            case R.id.search:
                break;
        }
    }
}
