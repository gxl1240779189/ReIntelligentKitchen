package com.gxl.intelligentkitchen.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食教学视频Item
 */
public class FoodTeachVideo extends BmobObject{

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public BmobFile getFoodvideo() {
        return foodvideo;
    }

    public void setFoodvideo(BmobFile foodvideo) {
        this.foodvideo = foodvideo;
    }

    String foodname;
    BmobFile foodvideo;
}
