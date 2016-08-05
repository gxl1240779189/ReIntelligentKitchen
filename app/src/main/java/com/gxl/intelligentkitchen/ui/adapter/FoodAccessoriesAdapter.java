package com.gxl.intelligentkitchen.ui.adapter;

import java.util.List;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodAccessoriesAdapter extends ArrayAdapter<FoodAccessories> {


	public FoodAccessoriesAdapter(Context context, int textViewResourceId,
								  List<FoodAccessories> objects) {
		super(context, textViewResourceId, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		FoodAccessories fuliao = getItem(position);
		View view;
		Viewholder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.foodaccessories_listview_item, null);
			viewholder = new Viewholder();
			viewholder.name = (TextView) view
					.findViewById(R.id.food_fuliao_name);
			viewholder.number = (TextView) view
					.findViewById(R.id.food_fuliao_shuliang);
			view.setTag(viewholder);
		} else {
			view = convertView;
			viewholder = (Viewholder) view.getTag();
		}
		viewholder.name.setText(fuliao.getName());
		viewholder.number.setText(fuliao.getNumber());
		return view;
	}

	class Viewholder {
		TextView name;
		TextView number;
	}

}
