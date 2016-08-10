package com.gxl.intelligentkitchen.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.presenter.FoodFragmentPresenter;
import com.gxl.intelligentkitchen.ui.activity.FoodLoveActivity;
import com.gxl.intelligentkitchen.ui.activity.FoodSearchActivity;
import com.gxl.intelligentkitchen.ui.activity.FoodTeachActivity;
import com.gxl.intelligentkitchen.ui.activity.FoodTypeActivity;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;
import com.gxl.intelligentkitchen.ui.customview.RoundImageView;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;
import com.gxl.intelligentkitchen.ui.view.IFoodFragment;
import com.gxl.intelligentkitchen.utils.NetUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


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
 * 作用：展示美食的页面
 */
public class FoodFragment extends Fragment implements IFoodFragment, XListView.IXListViewListener {

    @Bind(R.id.xListView)
    XListView xListView;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    RoundImageView writePhoto;
    ImageView dynamicPhoto;
    TextView dynamicText;
    TextView dynamicDetail;

    private int[] mIconList = {
            R.drawable.home_recipe, R.drawable.home_active,
            R.drawable.home_recipe_play, R.drawable.home_sign};
    private String[] mNameList = {"分类", "活动", "视频", "收藏"};
    private FoodGeneralAdapter mAdapter;
    private LinearLayout mFoodFragmentHead;
    private List<Map<String, Object>> mDataList;
    private ArrayList<FoodGeneralItem> mFoodGeneralList;
    private SimpleAdapter mSimpleAdapter;
    private FoodFragmentPresenter mFoodFragmentPresenter;
    private int mPage = 1;

    private GridView mGridview;
    private SlideShowView mSlideshowView;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    private final String FOODITEM = "fooditem";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.foodfragment_main, container, false);
        ButterKnife.bind(this, v);
        mFoodFragmentHead = (LinearLayout) inflater.inflate(R.layout.foodfragment_head, null);
        mGridview = (GridView) mFoodFragmentHead.findViewById(R.id.gridview);
        writePhoto = (RoundImageView) mFoodFragmentHead.findViewById(R.id.write_photo);
        dynamicText = (TextView) mFoodFragmentHead.findViewById(R.id.dynamic_text);
        dynamicDetail = (TextView) mFoodFragmentHead.findViewById(R.id.dynamic_detail);
        dynamicPhoto = (ImageView) mFoodFragmentHead.findViewById(R.id.dynamic_photo);
        mSlideshowView = (SlideShowView) mFoodFragmentHead.findViewById(R.id.slideshowView);
        xListView.addHeaderView(mFoodFragmentHead);
        init();
        return v;
    }

    private void init() {
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.xiaolian)
                .showImageForEmptyUri(R.drawable.xiaolian)
                .showImageOnFail(R.drawable.xiaolian).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        mFoodGeneralList = new ArrayList<>();
        mAdapter = new FoodGeneralAdapter(getActivity(), R.layout.general_listview_item, mFoodGeneralList);
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
        if (NetUtil.checkNet(getActivity())) {
            mFoodFragmentPresenter.onInitSliderShow();
            mFoodFragmentPresenter.onRefresh("update", 13, 1);
        } else {
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
            xListView.setVisibility(View.GONE);
        }

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getActivity(),
                                    FoodTypeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", position);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), FoodLoveActivity.class));
                            break;
                    }
                }
            }
        });

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FoodGeneralItem> mDatas = mAdapter.returnmDatas();
                FoodGeneralItem fooditem = mDatas.get(position - 2);
                Intent intent = new Intent(getActivity(), FoodTeachActivity.class);
                intent.putExtra("URLLINK", fooditem.getLink());
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
    public void onInitDynamic(DynamicItem item) {
        new UserModel().getUser(item.getWriter().getObjectId(), new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                imageLoader.displayImage(user.getPhoto().getUrl(), writePhoto, options);
                dynamicDetail.setText(user.getName());
            }

            @Override
            public void getFailure() {

            }
        });
        imageLoader.displayImage(item.getPhotoList().get(0).getUrl(), dynamicPhoto, options);
        dynamicText.setText(item.getText());
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

    @OnClick( R.id.search)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                startActivity(new Intent(getActivity(), FoodSearchActivity.class));
                break;
        }
    }

}
