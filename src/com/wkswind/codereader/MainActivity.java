package com.wkswind.codereader;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.wkswind.codereader.fileexplorer.FileListFragment;
import com.wkswind.codereader.model.RootInfo;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, FileListFragment.IFileSelected {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	public void onSectionAttached(RootInfo info) {
		
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public void onNavigationDrawerItemSelected(RootInfo info) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getSupportFragmentManager();
		Bundle args = new Bundle();
		args.putString(FileListFragment.CODE_TYPE, info.getIntent());
		args.putSerializable(FileListFragment.FILE_DIRECTORY, Environment.getExternalStorageDirectory());
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						FileListFragment.newInstance(args)).commit();		
	}

	@Override
	public void onFileSelected(File file) {
		// TODO Auto-generated method stub
		if(file.isFile()){
			startActivity(new Intent(this, ReaderActivity.class).setAction(Intent.ACTION_VIEW).setData(Uri.fromFile(file)));
		}
	}	

}
