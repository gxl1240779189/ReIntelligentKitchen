package com.gxl.intelligentkitchen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.presenter.FoodTypeFragmentPresenter;
import com.gxl.intelligentkitchen.ui.activity.FoodTeachActivity;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;
import com.gxl.intelligentkitchen.ui.view.IFoodFragment;
import com.gxl.intelligentkitchen.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.maxwin.view.XListView;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：不同种类的食品展示fragment
 */
public class FoodTypeFragment extends Fragment implements
        XListView.IXListViewListener, IFoodFragment {

    @Bind(R.id.xListView)
    XListView xListView;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    public FoodTypeFragment(String mSortBy, int mLM, int mPage) {
        this.mSortBy = mSortBy;
        this.mLM = mLM;
        this.mPage = mPage;
    }

    private String mSortBy;
    private int mLM;
    private int mPage;
    private int page;
    private FoodTypeFragmentPresenter mFoodTypeFragmentPresenter;
    private ArrayList<FoodGeneralItem> mFoodGeneralList;

    private FoodGeneralAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodtypefragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        mFoodGeneralList = new ArrayList<>();
        mAdapter = new FoodGeneralAdapter(getActivity(), R.layout.generalother_listview_item, mFoodGeneralList);
        xListView.setAdapter(mAdapter);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FoodGeneralItem> mDatas = mAdapter.returnmDatas();
                FoodGeneralItem fooditem = mDatas.get(position - 1);
                Intent intent = new Intent(getActivity(), FoodTeachActivity.class);
                intent.putExtra("URLLINK", fooditem.getLink());
                startActivity(intent);
            }
        });
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        mFoodTypeFragmentPresenter = new FoodTypeFragmentPresenter(this);
        if(NetUtil.checkNet(getActivity())){
            loading.setVisibility(View.VISIBLE);
            mFoodTypeFragmentPresenter.onRefresh(mSortBy, mLM, mPage);
        }else{
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
        }

        page = mPage;
    }

    @Override
    public void onRefresh() {
        mFoodTypeFragmentPresenter.onRefresh(mSortBy, mLM, mPage);
    }

    @Override
    public void onLoadMore() {
        page++;
        mFoodTypeFragmentPresenter.onLoadMore(mSortBy, mLM, page);
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
    }

    @Override
    public void onInitDynamic(DynamicItem item) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
