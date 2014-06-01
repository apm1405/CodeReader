package com.wkswind.codereader.utils;

import com.wkswind.codereader.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class OptionsMenuCheckBox extends CheckBox {

	public OptionsMenuCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setButtonDrawable(R.drawable.ic_action_starred_file);
	}

	public OptionsMenuCheckBox(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
		// TODO Auto-generated constructor stub
	}

	public OptionsMenuCheckBox(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
}
