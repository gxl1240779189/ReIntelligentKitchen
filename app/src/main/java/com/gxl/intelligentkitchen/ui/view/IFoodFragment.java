package com.gxl.intelligentkitchen.ui.view;

import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.ui.adapter.FoodGeneralAdapter;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：FoodFragment的View接口
 */
public interface IFoodFragment {
    //加载更多
     void onLoadMore(List<FoodGeneralItem> list);
    //下拉刷新
     void onRefresh(List<FoodGeneralItem> list);
    //初始化sliderView
    void onInitSliderShow(List<SlideShowView.SliderShowViewItem> list);
    //初始化最热动态
    void onInitDynamic(DynamicItem item);
}
