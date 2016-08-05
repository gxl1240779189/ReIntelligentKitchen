package com.gxl.intelligentkitchen.entity;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食教学的对象类
 */
public class FoodDetailTeachItem {
    public String FoodTitle;                 //美食名称
    public String FoodIntroduction;          //美食介绍

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public String FoodImage;                //美食图片
    public String WriteName;               //作者名
    public String WritePhoto;               //作者头像

    public String getWriteDate() {
        return WriteDate;
    }

    public void setWriteDate(String writeDate) {
        WriteDate = writeDate;
    }

    public String WriteDate;                //创作时间

    public String getFoodTitle() {
        return FoodTitle;
    }

    public void setFoodTitle(String foodTitle) {
        FoodTitle = foodTitle;
    }

    public String getFoodIntroduction() {
        return FoodIntroduction;
    }

    public void setFoodIntroduction(String foodIntroduction) {
        FoodIntroduction = foodIntroduction;
    }

    public String getWriteName() {
        return WriteName;
    }

    public void setWriteName(String writeName) {
        WriteName = writeName;
    }

    public String getWritePhoto() {
        return WritePhoto;
    }

    public void setWritePhoto(String writePhoto) {
        WritePhoto = writePhoto;
    }

    public List<FoodAccessories> getAccessoriesList() {
        return AccessoriesList;
    }

    public void setAccessoriesList(List<FoodAccessories> accessoriesList) {
        AccessoriesList = accessoriesList;
    }

    public List<FoodTeachStep> getStepList() {
        return StepList;
    }

    public void setStepList(List<FoodTeachStep> stepList) {
        StepList = stepList;
    }

    public List<FoodAccessories> AccessoriesList;          //做菜辅料
    public List<FoodTeachStep>  StepList;                  //做菜步骤

}
