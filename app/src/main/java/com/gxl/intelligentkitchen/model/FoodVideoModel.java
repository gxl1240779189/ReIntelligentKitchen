package com.gxl.intelligentkitchen.model;

import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.FoodTeachVideo;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.model.impl.FoodVideoModelImpl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：获取视频列表的model
 */
public class FoodVideoModel implements FoodVideoModelImpl {

    /**
     * 获取所有的播放视频的列表
     *
     * @param listener
     */
    @Override
    public void getGeneralFoodsVideoItem(final FoodModelImpl.BaseListener listener) {
        BmobQuery<FoodTeachVideo> query = new BmobQuery<FoodTeachVideo>();
        query.findObjects(BaseApplication.getmContext(), new FindListener<FoodTeachVideo>() {
            @Override
            public void onSuccess(List<FoodTeachVideo> object) {
                listener.getSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }
}
