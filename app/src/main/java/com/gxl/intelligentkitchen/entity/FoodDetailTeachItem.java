package com.gxl.intelligentkitchen.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食教学的对象类
 */
@Table(name = "FoodDetailTeachItem")
public class FoodDetailTeachItem extends Model implements Serializable {

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "foodId")
    String foodId;

    @Column(name = "FoodTitle")
    public String FoodTitle;                 //美食名称

    @Column(name = "FoodIntroduction")
    public String FoodIntroduction;          //美食介绍


    @Column(name = "FoodImage")
    public String FoodImage;                //美食图片

    @Column(name = "WriteName")
    public String WriteName;               //作者名

    @Column(name = "WritePhoto")
    public String WritePhoto;               //作者头像

    @Column(name = "WriteDate")
    public String WriteDate;                //创作时间

    public List<FoodAccessories> AccessoriesList;          //做菜辅料

    public List<FoodTeachStep> StepList;                  //做菜步骤


    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public String getWriteDate() {
        return WriteDate;
    }

    public void setWriteDate(String writeDate) {
        WriteDate = writeDate;
    }

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

    /**
     * 从数据库中获取教学步骤
     */
    public List<FoodTeachStep> getTeachStepFromSQL() {
        return new Select().from(FoodTeachStep.class).where("foodId=?", foodId).execute();
    }

    /**
     * 从数据库中获取美食材料
     */
    public List<FoodAccessories> getFoodAccessoriesFromSQL() {
        return new Select().from(FoodAccessories.class).where("foodId=?", foodId).execute();
    }

}
