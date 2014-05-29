package com.wkswind.codereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebChrome2 extends WebViewClient {
	private ProgressDialog pd;
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		
		Context context = view.getContext();
		pd = ProgressDialog.show(context, context.getString(R.string.loading),context.getString(R.string.loading));
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		if(pd != null){
			if(pd.isShowing()){
				pd.dismiss();
			}
			pd = null;
		}
	}
}
