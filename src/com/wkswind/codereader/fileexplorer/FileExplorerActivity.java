package com.wkswind.codereader.fileexplorer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import com.wkswind.codereader.MainActivity;
import com.wkswind.codereader.R;
import com.wkswind.codereader.fileexplorer.FileListFragment.IFileSelected;

public class FileExplorerActivity extends ActionBarActivity implements
		IFileSelected {

	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * final Resources res = getResourcses(); boolean mShowAsDialog =
		 * res.getBoolean(R.bool.show_as_dialog); if (mShowAsDialog) { if
		 * (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) { //
		 * backgroundDimAmount from theme isn't applied; do it manually final
		 * WindowManager.LayoutParams a = getWindow() .getAttributes();
		 * a.dimAmount = 0.6f; getWindow().setAttributes(a);
		 * 
		 * getWindow().setFlags(0,
		 * WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		 * getWindow().setFlags(~0, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		 * 
		 * // Inset ourselves to look like a dialog final Point size = new
		 * Point(); getWindowManager().getDefaultDisplay().getSize(size); final
		 * int width = (int) res.getFraction(R.dimen.dialog_width, size.x,
		 * size.x); final int height = (int)
		 * res.getFraction(R.dimen.dialog_height, size.y, size.y); final int
		 * insetX = (size.x - width) / 2; final int insetY = (size.y - height) /
		 * 2;
		 * 
		 * final Drawable before = getWindow().getDecorView() .getBackground();
		 * final Drawable after = new InsetDrawable(before, insetX, insetY,
		 * insetX, insetY); // getWindow().getDecorView().setBackground(after);
		 * getWindow().getDecorView().setBackgroundDrawable(after);
		 * 
		 * // Dismiss when touch down in the dimmed inset area
		 * getWindow().getDecorView().setOnTouchListener( new OnTouchListener()
		 * {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { if
		 * (event.getAction() == MotionEvent.ACTION_DOWN) { final float x =
		 * event.getX(); final float y = event.getY(); if (x < insetX || x >
		 * v.getWidth() - insetX || y < insetY || y > v.getHeight() - insetY) {
		 * finish(); return true; } } return false; } }); }
		 * 
		 * }
		 */

		if (savedInstanceState == null) {
			fm = getSupportFragmentManager();
			Bundle extras = new Bundle();
			extras.putSerializable(FileListFragment.FILE_DIRECTORY,
					Environment.getExternalStorageDirectory());
			Fragment fragment = FileListFragment.newInstance(extras);
			fm.beginTransaction()
					.add(android.R.id.content, fragment, fragment.getTag())
					.addToBackStack(
							Environment.getExternalStorageDirectory()
									.getAbsolutePath()).commit();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		int count = fm.getBackStackEntryCount();

		if (count > 1) {
			// getSupportActionBar().setSubtitle(
			// fm.getBackStackEntryAt(count - 2).getName());
			fm.popBackStack();
		} else {
			finish();
		}
	}

	@Override
	public void onFileSelected(File file) {
		// TODO Auto-generated method stub
		if (file.isDirectory()) {
			Bundle args = new Bundle();
			args.putSerializable(FileListFragment.FILE_DIRECTORY, file);
			Fragment fragment = FileListFragment.newInstance(args);
			fm.beginTransaction().replace(android.R.id.content, fragment)
			// .add(android.R.id.content, fragment)
					.addToBackStack(file.getAbsolutePath()).commit();
		} else {
			if (file.length() > MainActivity.MAXFILESIZE) {
				// Toast.makeText(context, text, duration)
				Toast.makeText(this, getResources().getString(R.string.file_too_large, FileAdapter.readableFileSize(MainActivity.MAXFILESIZE)), Toast.LENGTH_SHORT)
				.show();
				return;
			}
			final Intent intent = new Intent();
			intent.setData(Uri.fromFile(file));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

}
