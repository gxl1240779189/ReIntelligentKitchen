package com.gxl.intelligentkitchen.model.impl;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：获取视频列表的接口
 */
public interface FoodVideoModelImpl {
    /**
     * 获取美食视频的大概信息
     *
     * @param listener
     */
    void getGeneralFoodsVideoItem(FoodModelImpl.BaseListener listener);
}

