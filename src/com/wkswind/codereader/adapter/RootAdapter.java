package com.wkswind.codereader.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.model.RootInfo;

public class RootAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<RootInfo> infos;
	public RootAdapter(Context context){
		inflater = LayoutInflater.from(context);
		infos = RootInfo.init(context.getResources());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos == null ? 0 : infos.size();
	}
	@Override
	public RootInfo getItem(int position) {
		// TODO Auto-generated method stub
		return infos == null ? null : infos.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RootHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_root_list, null);
			holder = new RootHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon_mime);
			holder.title = (TextView) convertView.findViewById(android.R.id.title);
			convertView.setTag(holder);
		}else{
			holder = (RootHolder) convertView.getTag();
		}
		RootInfo info = getItem(position);
		if(info != null){
			holder.title.setText(info.getTitle());
			holder.icon.setBackgroundResource(info.getIcon());
		}
		
		return convertView;
	}
	
	static class RootHolder{
		TextView title;
		ImageView icon;
	}
}
