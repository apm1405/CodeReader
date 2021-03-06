package com.wkswind.codereader.fileexplorer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.fileexplorer.sort.SortFolder;
import com.wkswind.codereader.fileexplorer.sort.SortType;

public class FileListFragment extends Fragment implements LoaderCallbacks<List<File>>{
	
	public static final String FILE_DIRECTORY = "file_directory";
	private int LOADER_ID = 1;
	
	public static FileListFragment newInstance(Bundle args){
		FileListFragment fragment = new FileListFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private ListView list;
	private View progressView;
	private FileAdapter adapter;
	private IFileSelected iFileSelected;
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.file, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.sort_by_name:
			adapter.sort(SortType.byDefault);
			break;
		case R.id.sort_by_date:
			adapter.sort(SortType.byDate);
			break;
		case R.id.sort_by_size:
			adapter.sort(SortType.bySize);
			break;
		case R.id.action_refresh:
			query();
		default:
			break;
		}
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(((File)getArguments().getSerializable(FILE_DIRECTORY)).getAbsolutePath());
		setHasOptionsMenu(true);
		query();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_fileexplorer, container, false);
		list = (ListView) view.findViewById(R.id.file_list);
		progressView = view.findViewById(R.id.progress);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(iFileSelected != null){
					iFileSelected.onFileSelected(adapter.getItem(position));
				}
			}
		});
		return view;
	}
	
	static interface IFileSelected{
		public void onFileSelected(File file);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof IFileSelected){
			iFileSelected = (IFileSelected) activity;
		}else{
			throw new IllegalArgumentException("activity not instanceof IFileSelected ");
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		iFileSelected = null;
	}	
	
	void query(){
		showProgress(true);
		LoaderManager manager = getLoaderManager();
		if(manager.getLoader(LOADER_ID) == null){
			manager.initLoader(LOADER_ID, getArguments(), this);
		}else{
			manager.restartLoader(LOADER_ID, getArguments(), this);
		}
	}
	
	void showProgress(boolean show){
		if(progressView != null){
			progressView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
		if(list != null){
			list.setVisibility(show ? View.GONE : View.VISIBLE);
		}
		
	}
	
	static class FileLoader extends AsyncTaskLoaderEx<List<File>>{

		public FileLoader(Context context, Bundle extras) {
			super(context, extras);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<File> loadDataInBackground(Bundle extras) {
			// TODO Auto-generated method stub
			File file = (File) extras.getSerializable(FILE_DIRECTORY);
			FilenameFilter filter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String filename) {
					// TODO Auto-generated method stub
					return !filename.startsWith(".");
				}
			};
			List<File> rst = new ArrayList<File>();
			if(file.exists() && file != null){
				rst = Arrays.asList(file.listFiles(filter));
				Collections.sort(rst, new SortFolder());
				return rst;
			}
			return null;
		}
		
	}

	@Override
	public Loader<List<File>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new FileLoader(getActivity(), args);
	}

	@Override
	public void onLoadFinished(Loader<List<File>> loader, List<File> data) {
		// TODO Auto-generated method stub		
		adapter = new FileAdapter(getActivity(), data);
		list.setAdapter(adapter);
		showProgress(false);
	}

	@Override
	public void onLoaderReset(Loader<List<File>> loader) {
		// TODO Auto-generated method stub
		adapter.reset();
		adapter.notifyDataSetChanged();
	}
}
