package com.gxl.intelligentkitchen.ui.adapter;

import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;

public class SearchHistoryAdapter extends ArrayAdapter<String> {

	Context context;
	int textViewResourceId;
	List<String> objects;
	public SearchHistoryAdapter(Context context, int textViewResourceId,
								List<String> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.textViewResourceId=textViewResourceId;
		this.objects=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=null;
		ViewHolder viewholder;
		if(convertView==null)
		{
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(textViewResourceId, null);
			viewholder.searchhistory_item=(TextView) view.findViewById(R.id.search_history_item);
			view.setTag(viewholder);
		}else
		{
			view=convertView;
			viewholder=(ViewHolder) view.getTag();
		}
		viewholder.searchhistory_item.setText(objects.get(position));
		return view;
	}
	
    class ViewHolder
	{
		 TextView searchhistory_item;
	}

}


