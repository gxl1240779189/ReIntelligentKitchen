package com.gxl.intelligentkitchen.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.gxl.intelligentkitchen.model.impl.FoodLoveModelImpl;
import com.gxl.intelligentkitchen.utils.StringUtils;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：收藏美食的实现
 */
public class FoodLoveModel implements FoodLoveModelImpl {
    @Override
    public List<FoodDetailTeachItem> onQuery() {
        return new Select().from(FoodDetailTeachItem.class).execute();
    }

    @Override
    public void onInsert(FoodDetailTeachItem item) {
        String foodId = StringUtils.BuildOrderNum();
        item.setFoodId(foodId);
        item.save();
        List<FoodAccessories> AccessoriesList = item.getAccessoriesList();
        ActiveAndroid.beginTransaction();
        try {
            for (FoodAccessories accessoriesItem : AccessoriesList
                    ) {
                accessoriesItem.setFoodId(foodId);
                accessoriesItem.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        List<FoodTeachStep> teachStepList = item.getStepList();
        ActiveAndroid.beginTransaction();
        try {
            for (FoodTeachStep teachStepItem : teachStepList
                    ) {
                teachStepItem.setFoodId(foodId);
                teachStepItem.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public void onDelete(FoodDetailTeachItem item) {
            FoodDetailTeachItem sqlItem = new Select().from(FoodDetailTeachItem.class).where("FoodTitle=?", item.FoodTitle).where("FoodImage=?", item.getFoodImage()).executeSingle();
            String foodId = sqlItem.getFoodId();
            sqlItem.delete();
            new Delete().from(FoodAccessories.class).where("foodId=?", foodId).execute();
            new Delete().from(FoodTeachStep.class).where("foodId=?", foodId).execute();
    }

    @Override
    public boolean onQuery(FoodDetailTeachItem item) {
        FoodDetailTeachItem sqlItem = new Select().from(FoodDetailTeachItem.class).where("FoodTitle=?", item.FoodTitle).where("FoodImage=?", item.getFoodImage()).executeSingle();
        if (sqlItem != null)
            return true;
        return false;
    }
}
