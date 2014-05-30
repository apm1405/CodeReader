package com.wkswind.codereader.fileexplorer;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.fileexplorer.sort.SortFolder;
import com.wkswind.codereader.fileexplorer.sort.SortModifyDate;
import com.wkswind.codereader.fileexplorer.sort.SortName;
import com.wkswind.codereader.fileexplorer.sort.SortSize;
import com.wkswind.codereader.fileexplorer.sort.SortType;

public class FileAdapter extends BaseAdapter {
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("MM-dd", Locale.getDefault());
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("hh:mm", Locale.getDefault());
	private LayoutInflater inflater;
	private List<File> files;
	private Context context;
	public FileAdapter(Context context, List<File> files) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.files = files;
	}
	
	public void reset(){
		files = null;
	}
	
	
	
	public void setFiles(List<File> files) {
		this.files = files;
//		notifyDataSetChanged();
	}

	public void sort(SortType type){
		if(type == SortType.byDefault){
			Collections.sort(files, new SortFolder());
		}else if(type == SortType.byDate){
			Collections.sort(files, new SortModifyDate());
		}else if(type == SortType.byName){
			Collections.sort(files, new SortName());			
		}else if(type == SortType.bySize){
			Collections.sort(files, new SortSize());
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files == null ? 0 : files.size();
	}

	@Override
	public File getItem(int position) {
		// TODO Auto-generated method stub
		return files == null ? null : files.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_doc_list, parent,
					false);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		File item = getItem(position);
		holder.iconMime = (ImageView) convertView
				.findViewById(R.id.icon_mime);
		holder.title = (TextView) convertView
				.findViewById(android.R.id.title);
		holder.date = (TextView) convertView.findViewById(R.id.date);
		holder.size = (TextView) convertView.findViewById(R.id.size);
		holder.title.setText(item.getName());
		long lastModify = item.lastModified();
//		if(DateUtils.isToday(lastModify))
		holder.date.setText(DateUtils.isToday(lastModify) ?  FORMAT_TIME.format(new Date(item.lastModified())):FORMAT_DATE.format(new Date(item.lastModified())));
		if(item.isDirectory()){
			holder.size.setVisibility(View.GONE);
		}else{
			holder.size.setVisibility(View.VISIBLE);;
			holder.size.setText(readableFileSize(item.length()));
		}
//		holder.size.setText(readableFileSize(getFileSize(item)));
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
			holder.iconMime.setBackground(IconUtils.loadMimeIcon(context, item));
		}else{
			holder.iconMime.setBackgroundDrawable(IconUtils.loadMimeIcon(context, item));
		}
		return convertView;

	}
	
	static class ViewHolder{
		ImageView iconMime;
		TextView title, date, size;
	}
	
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
}
