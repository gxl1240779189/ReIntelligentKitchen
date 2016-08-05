package com.gxl.intelligentkitchen.ui.adapter;

import java.util.List;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodTeachStepAdapter extends ArrayAdapter<FoodTeachStep> {

	public int textViewResourceId;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public FoodTeachStepAdapter(Context context, int textViewResourceId,
								List<FoodTeachStep> objects) {
		super(context, textViewResourceId, objects);
		this.textViewResourceId = textViewResourceId;
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiaolian)
				.showImageForEmptyUri(R.drawable.xiaolian)
				.showImageOnFail(R.drawable.xiaolian).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        FoodTeachStep item = getItem(position);
		View view = null;
		Viewholder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(
					textViewResourceId, null);
			viewholder = new Viewholder();
			viewholder.num = (TextView) view.findViewById(R.id.num);
			viewholder.teachimage = (ImageView) view
					.findViewById(R.id.teach_image);
			viewholder.teachtext = (TextView) view
					.findViewById(R.id.teach_text);
			view.setTag(viewholder);
		} else {
			view = convertView;
			viewholder = (Viewholder) view.getTag();
		}
		viewholder.num.setText(item.getNum());
		if (!item.imagelink.equals("noimagelink")) {
			viewholder.teachimage.setVisibility(View.VISIBLE);
			imageLoader.displayImage(item.imagelink, viewholder.teachimage,
					options);
		}

		viewholder.teachtext.setText(item.getTeachtext());
		return view;
	}

	class Viewholder {
		TextView num;
		ImageView teachimage;
		TextView teachtext;
	}
}
