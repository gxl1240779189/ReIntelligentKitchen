package com.gxl.intelligentkitchen.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.FoodTeachVideo;
import com.gxl.intelligentkitchen.model.FoodVideoModel;

import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：展示可以播放的视频列表
 */
public class FoodVideoAdapter extends BaseAdapter {

    private List<FoodTeachVideo> mList;

    public FoodVideoAdapter(List<FoodTeachVideo> mList) {
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(BaseApplication.getmContext()).inflate(R.layout.foodvideo_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.foodname);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        FoodTeachVideo item = (FoodTeachVideo) getItem(position);
        viewHolder.textView.setText(item.getFoodname());
        return view;
    }

    public static class ViewHolder {
        TextView textView;
    }
}
