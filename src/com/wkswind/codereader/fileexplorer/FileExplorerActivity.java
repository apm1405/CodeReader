package com.wkswind.codereader.fileexplorer;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.wkswind.codereader.R;
import com.wkswind.codereader.ReaderActivity;
import com.wkswind.codereader.fileexplorer.FileExplorerFragment.IFileSelected;

public class FileExplorerActivity extends ActionBarActivity implements
		IFileSelected, OnBackStackChangedListener {

	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			fm = getSupportFragmentManager();
			Bundle extras = new Bundle();
			extras.putSerializable(FileExplorerFragment.FILE_DIRECTORY,
					Environment.getExternalStorageDirectory());
//			Fragment fragment = FileListFragment.newInstance(extras);
			Fragment fragment = FileExplorerFragment.newInstance(extras);
			fm.addOnBackStackChangedListener(this);
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
			args.putSerializable(FileExplorerFragment.FILE_DIRECTORY, file);
			Fragment fragment = FileExplorerFragment.newInstance(args);
			fm.beginTransaction().replace(android.R.id.content, fragment)
			// .add(android.R.id.content, fragment)
					.addToBackStack(file.getAbsolutePath()).commit();
		} else {
			if (file.length() > ReaderActivity.MAXFILESIZE) {
				// Toast.makeText(context, text, duration)
				Toast.makeText(this, getResources().getString(R.string.file_too_large, FileAdapter.readableFileSize(ReaderActivity.MAXFILESIZE)), Toast.LENGTH_SHORT)
				.show();
				return;
			}
			startActivity(new Intent(this, ReaderActivity.class).setAction(Intent.ACTION_VIEW).setData(Uri.fromFile(file)));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		Log.i("TAG", "onBackStackChanged");
	}

}
