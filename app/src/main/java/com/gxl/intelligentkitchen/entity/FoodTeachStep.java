package com.gxl.intelligentkitchen.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食教学的步骤类
 */

@Table(name = "FoodTeachStep")
public class FoodTeachStep extends Model implements Serializable{

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Column(name = "foodId")
    String foodId;

    @Column(name = "num")
    public String num;
    @Column(name = "imagelink")
    public String imagelink;
    @Column(name = "teachtext")
    public String teachtext;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getTeachtext() {
        return teachtext;
    }

    public void setTeachtext(String teachtext) {
        this.teachtext = teachtext;
    }

    @Override
    public String toString() {
        return imagelink + "  " + teachtext;
    }
}
