package com.gxl.intelligentkitchen.entity;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食教学的辅料对象
 */

@Table(name="FoodAccessories")
public class FoodAccessories extends Model implements Serializable{
	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	@Column(name="foodId")
	String foodId;
	@Column(name="name")
	String  name;
	@Column(name="number")
	String number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return name + " --" + number;
	}

}
