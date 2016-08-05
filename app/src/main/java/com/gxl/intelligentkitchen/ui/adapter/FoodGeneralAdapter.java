package com.gxl.intelligentkitchen.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodGeneralAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<FoodGeneralItem> mDatas;

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public FoodGeneralAdapter(Context context, List<FoodGeneralItem> datas) {
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}



	public List<FoodGeneralItem> returnmDatas() {
		return this.mDatas;
	}

	public void addAll(List<FoodGeneralItem> mDatas) {
		this.mDatas.addAll(mDatas);
	}

	public void setDatas(List<FoodGeneralItem> mDatas) {
		this.mDatas.clear();
		this.mDatas.addAll(mDatas);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.general_listview_item, null);
			holder = new ViewHolder();
			holder.mTaste = (TextView) convertView
					.findViewById(R.id.food_taste);
			holder.mTitle = (TextView) convertView
					.findViewById(R.id.food_title);
			holder.mImg = (ImageView) convertView
					.findViewById(R.id.food_newsImg);
			holder.mPersonname = (TextView) convertView
					.findViewById(R.id.food_personname);
			holder.mDate = (TextView) convertView.findViewById(R.id.food_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FoodGeneralItem foodsitem = mDatas.get(position);
		holder.mTitle.setText(foodsitem.getTitle());
		holder.mTaste.setText(foodsitem.getTaste());
		holder.mPersonname.setText(foodsitem.getWriter());
		holder.mDate.setText(foodsitem.getDate());
		imageLoader.displayImage(foodsitem.getImgLink(), holder.mImg, options);
		return convertView;
	}

	private final class ViewHolder {
		TextView mTitle;
		TextView mTaste;
		ImageView mImg;
		TextView mPersonname;
		TextView mDate;
	}

}
