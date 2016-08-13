package com.gxl.intelligentkitchen.ui.view;

import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：DynamicFragment的View接口
 */
public interface IDynamicFragment {
    //加载更多
    void onLoadMore(List<DynamicItem> list);

    //下拉刷新
    void onRefresh(List<DynamicItem> list);
}
