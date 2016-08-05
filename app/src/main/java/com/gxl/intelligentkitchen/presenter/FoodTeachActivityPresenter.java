package com.gxl.intelligentkitchen.presenter;

import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.model.FoodModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.view.IFoodFragment;
import com.gxl.intelligentkitchen.ui.view.IFoodTeachActivity;

import java.util.ArrayList;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class FoodTeachActivityPresenter {
    private IFoodTeachActivity mIFoodTeachActivity;
    private FoodModel mFoodModel=new FoodModel();

    public FoodTeachActivityPresenter(IFoodTeachActivity mIFoodTeachActivity) {
        this.mIFoodTeachActivity = mIFoodTeachActivity;
    }

    public void onLoadData(String foodLink){
        mFoodModel.getFoodDetailTeachItem(foodLink, new FoodModelImpl.BaseListener() {
            @Override
            public void getSuccess(Object o) {
                FoodDetailTeachItem foodDetailTeachItem= (FoodDetailTeachItem) o;
                mIFoodTeachActivity.onLoadData(foodDetailTeachItem);
            }

            @Override
            public void getFailure() {

            }
        });
    }
}
