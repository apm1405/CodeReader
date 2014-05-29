package com.wkswind.codereader.fileexplorer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncTaskLoaderEx<T> extends AsyncTaskLoader<T> {
	private T oldData = null;
	private Bundle extras;
	/**
	 * 
	 * @param context
	 */
	public AsyncTaskLoaderEx(Context context, Bundle extras) {
		super(context);
		// TODO Auto-generated constructor stub
		this.extras = extras;
	}

	@Override
	public T loadInBackground() {
		// TODO Auto-generated method stub
		oldData = loadDataInBackground(extras);
		return oldData;
	}
	
	protected abstract T loadDataInBackground(Bundle extras);

	@Override
	public void deliverResult(T data) {
		// TODO Auto-generated method stub
		super.deliverResult(data);
		if(isReset()){
			if(data != null){
				//release resources
				releaseResources(data);
			}
		}
		
		T oldEntities = data;
		this.oldData = data;
		if(isStarted()){
			super.deliverResult(data);
		}
		
		if(oldEntities != null){
			//release resources
			releaseResources(data);
		}
	}
	
	@Override
	public void onCanceled(T data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
		cancelLoad();
	}
	
	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		super.onStartLoading();
		if(oldData != null){
			deliverResult(oldData);
		}else{
			forceLoad();
		}
	}
	
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
		onStopLoading();
		if(oldData != null){
			releaseResources(oldData);
		}
	}
	/**
	 * release resources.if cursor, then should close here;if List<>,then nothing todo
	 * @author:admin
	 * @date:2013-9-29 下午8:29:42
	 */
	protected void releaseResources(T data) {
		// TODO Auto-generated method stub
		
	}

}
