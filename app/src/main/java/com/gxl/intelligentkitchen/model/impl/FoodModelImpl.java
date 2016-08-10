package com.gxl.intelligentkitchen.model.impl;

import com.gxl.intelligentkitchen.entity.FoodGeneralItem;

import java.util.ArrayList;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：FoodModel接口
 */
public interface FoodModelImpl {
    /**
     * 获取美食的大概信息
     * @param sortby
     * @param type
     * @param page
     * @param listener
     */
         void getGeneralFoodsItem(String sortby,int type,int page,BaseListener listener);

    /**
     * 获取美食的详细做法
     * @param foodlink
     * @param listener
     */
         void getFoodDetailTeachItem(String foodlink,BaseListener listener);


    /**
     * 获取滚动展示的数据集合
     * @param listener
     */
         void  getSliderShowFood(BaseListener listener);

    interface BaseListener<T>{
             void getSuccess(T t);
             void getFailure();
        }
}
