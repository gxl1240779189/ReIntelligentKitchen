package com.gxl.intelligentkitchen.model.impl;

import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：收藏美食接口
 */
public interface FoodLoveModelImpl {
    //查询所有
    List<FoodDetailTeachItem> onQuery();
    //增
    void onInsert(FoodDetailTeachItem item);
    //删
    void onDelete(FoodDetailTeachItem item);

    //查询是否存在
    boolean onQuery(FoodDetailTeachItem item);
}
