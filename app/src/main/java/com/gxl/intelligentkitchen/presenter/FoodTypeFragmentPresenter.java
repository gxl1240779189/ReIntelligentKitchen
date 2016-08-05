package com.gxl.intelligentkitchen.presenter;

import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.model.FoodModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.view.IFoodFragment;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class FoodTypeFragmentPresenter {

    private IFoodFragment mIFoodFragment;
    private FoodModel mFoodModel=new FoodModel();

    public FoodTypeFragmentPresenter(IFoodFragment mIFoodFragment) {
        this.mIFoodFragment = mIFoodFragment;
    }

    /**
     * 上拉加载更多
     * @param sortby
     * @param lm
     * @param page
     */
    public void onLoadMore(String sortby,int lm,int page){
        mFoodModel.getGeneralFoodsItem(sortby, lm, page, new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<FoodGeneralItem> list= (List<FoodGeneralItem>) o;
                mIFoodFragment.onLoadMore(list);
            }

            @Override
            public void getFailure() {
            }
        });
    }

    /**
     * 下拉刷新
     * @param sortby
     * @param lm
     * @param page
     */
    public void onRefresh(String sortby,int lm,int page){
        mFoodModel.getGeneralFoodsItem(sortby, lm, page, new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                List<FoodGeneralItem> list= (List<FoodGeneralItem>) o;
                mIFoodFragment.onRefresh(list);
            }

            @Override
            public void getFailure() {
            }
        });
    }

}
