package com.gxl.intelligentkitchen.model.impl;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：搜索美食接口
 */
public interface FoodSearchModelImpl {
    void onSearchFood(String foodname, FoodModelImpl.BaseListener listener);
}
