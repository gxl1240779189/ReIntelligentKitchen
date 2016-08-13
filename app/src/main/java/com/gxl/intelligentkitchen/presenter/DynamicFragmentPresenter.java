package com.gxl.intelligentkitchen.presenter;

import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.model.DynamicModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.view.IDynamicFragment;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：朋友圈的Presenter
 */
public class DynamicFragmentPresenter {
    private DynamicModel mDynamicModel = new DynamicModel();
    private IDynamicFragment mView;

    public DynamicFragmentPresenter(IDynamicFragment mView) {
        this.mView = mView;
    }

    public void onRefresh(){
        mDynamicModel.getDynamicItem(new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<DynamicItem> list= (List<DynamicItem>) o;
                mView.onRefresh(list);
            }

            @Override
            public void getFailure() {
            }
        });
    }

    public void onLoadMore(){

    }

}
