package com.gxl.intelligentkitchen.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：美食搜索Json类
 */
public class FoodSearchJson implements Serializable {
	String resultcode;
	String reason;
	Result result;
	int error_code;

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public static class Result implements Serializable {
		List<foodItem> data;
		String totalNum;
		int pn;
		String rn;

		public List<foodItem> getData() {
			return data;
		}

		public void setData(List<foodItem> data) {
			this.data = data;
		}

		public String getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(String totalNum) {
			this.totalNum = totalNum;
		}

		public int getPn() {
			return pn;
		}

		public void setPn(int pn) {
			this.pn = pn;
		}

		public String getRn() {
			return rn;
		}

		public void setRn(String rn) {
			this.rn = rn;
		}
	}

	public static class foodItem implements Serializable {
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getImtro() {
			return imtro;
		}

		public void setImtro(String imtro) {
			this.imtro = imtro;
		}

		public String getIngredients() {
			return ingredients;
		}

		public void setIngredients(String ingredients) {
			this.ingredients = ingredients;
		}

		public String getBurden() {
			return burden;
		}

		public void setBurden(String burden) {
			this.burden = burden;
		}

		public List<String> getAlbums() {
			return albums;
		}

		public void setAlbums(List<String> albums) {
			this.albums = albums;
		}

		public List<Steps> getSteps() {
			return steps;
		}

		public void setSteps(List<Steps> steps) {
			this.steps = steps;
		}

		String id;
		String title;
		String tags;
		String imtro;
		String ingredients;
		String burden;
		List<String> albums;
		List<Steps> steps;
	}

	public static class Steps implements Serializable {
		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getStep() {
			return step;
		}

		public void setStep(String step) {
			this.step = step;
		}

		String img;
		String step;
	}
}
