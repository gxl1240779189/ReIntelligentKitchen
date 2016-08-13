package com.gxl.intelligentkitchen.model.impl;

import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.User;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public interface DynamicModelImpl {
    void getDynamicItem(FoodModelImpl.BaseListener listener);
    void getDynamicItemByPhone(User user, FoodModelImpl.BaseListener listener);
}
