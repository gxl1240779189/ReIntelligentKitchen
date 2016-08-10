package com.gxl.intelligentkitchen.model.impl;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：获取用户接口
 */
public interface UserModelImpl {
    void getUser(String name,String passoword,FoodModelImpl.BaseListener listener);
    void getUser(String objectId, final FoodModelImpl.BaseListener listener);
}
