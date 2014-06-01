package com.wkswind.codereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.wkswind.codereader.database.data.provider.ReadingHistoryContent;
import com.wkswind.codereader.database.data.provider.ReadingHistoryContent.Wish.Columns;
import com.wkswind.codereader.fileexplorer.FileAdapter;
import com.wkswind.codereader.synatax.CDocumentHandler;
import com.wkswind.codereader.synatax.CppDocumentHandler;
import com.wkswind.codereader.synatax.CssDocumentHandler;
import com.wkswind.codereader.synatax.DocumentHandler;
import com.wkswind.codereader.synatax.HtmlDocumentHandler;
import com.wkswind.codereader.synatax.JavaDocumentHandler;
import com.wkswind.codereader.synatax.JavascriptDocumentHandler;
import com.wkswind.codereader.synatax.LispDocumentHandler;
import com.wkswind.codereader.synatax.LuaDocumentHandler;
import com.wkswind.codereader.synatax.MlDocumentHandler;
import com.wkswind.codereader.synatax.MxmlDocumentHandler;
import com.wkswind.codereader.synatax.PerlDocumentHandler;
import com.wkswind.codereader.synatax.PythonDocumentHandler;
import com.wkswind.codereader.synatax.RubyDocumentHandler;
import com.wkswind.codereader.synatax.SqlDocumentHandler;
import com.wkswind.codereader.synatax.TextDocumentHandler;
import com.wkswind.codereader.synatax.VbDocumentHandler;
import com.wkswind.codereader.synatax.XmlDocumentHandler;
import com.wkswind.utils.PrefsUtils;

public class ReaderActivity extends ActionBarActivity {
	private Uri selectedFile;
	public static final int REQUEST_FILE_CHOOSE = 0;
	private WebView codeReader;

	public static final int MAXFILESIZE = 1024 * 128;
	private static final String LAST_READ = "last_read";
	private boolean isStarred = false;

	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		codeReader = (WebView) findViewById(R.id.code_reader);
		codeReader.setWebViewClient(new WebChrome2());
		WebSettings s = codeReader.getSettings();
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		s.setUseWideViewPort(false);
		s.setAllowFileAccess(true);
		s.setLightTouchEnabled(true);
		s.setLoadsImagesAutomatically(true);
		s.setSupportZoom(true);
		s.setSupportMultipleWindows(true);
		s.setJavaScriptEnabled(true);
		String action = getIntent().getAction();
		if (Intent.ACTION_VIEW.equals(action) || Intent.ACTION_OPEN_DOCUMENT.equals(action)) {
			selectedFile = getIntent().getData();
			if (selectedFile != null
					&& selectedFile.toString().startsWith("file")) {
				loadFile(selectedFile);
			}
		} else {
			Uri lastUri = null;
			String lastRead = PrefsUtils.get(this, LAST_READ, "");
			if (!TextUtils.isEmpty(lastRead)) {
				lastUri = Uri.parse(lastRead);
			}

			if (savedInstanceState != null) {
				codeReader.restoreState(savedInstanceState);
				lastRead = savedInstanceState.getString(LAST_READ);
				if (!TextUtils.isEmpty(lastRead)) {
					lastUri = Uri.parse(lastRead);
				}
			}

			if (lastUri != null) {
				loadFile(lastUri);
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (selectedFile != null) {
			outState.putString(LAST_READ, String.valueOf(selectedFile));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (selectedFile != null) {
			PrefsUtils.put(this, LAST_READ, selectedFile.toString());
		}
		codeReader.getSettings().setBuiltInZoomControls(false);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reader, menu);
		MenuItem menuItem = menu.findItem(R.id.action_share);
		// Get the provider and hold onto it to set/change the share intent.
		ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(menuItem);
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		// Note that you can set/change the intent any time,
		// say when the user has selected an image.
		actionProvider.setShareIntent(createShareIntent());
		
		MenuItem starredItem = menu.findItem(R.id.action_starred);
		CheckBox chkStarred = (CheckBox) MenuItemCompat.getActionView(starredItem);
		chkStarred.setClickable(true);
		chkStarred.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(ReaderActivity.this, "starred", Toast.LENGTH_SHORT).show();
				isStarred = isChecked;
			}
		});
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.action_share).setEnabled( selectedFile != null);
		return super.onPrepareOptionsMenu(menu);
	}

	private Intent createShareIntent() {
		// TODO Auto-generated method stub
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, selectedFile);
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_FILE_CHOOSE) {
				selectedFile = data.getData();
				loadFile(selectedFile);
			}
		}
	}

	private void loadFile(Uri uri) {
		// TODO Auto-generated method stub
		File file = new File(uri.getPath());
		if (!file.exists()) {
			Toast.makeText(this, R.string.file_not_exists, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (file.length() > MAXFILESIZE) {
			Toast.makeText(this, getResources().getString(R.string.file_too_large, FileAdapter.readableFileSize(MAXFILESIZE)), Toast.LENGTH_SHORT)
					.show();
			return;
		}
		DocumentHandler handler = getHandlerByFileExtension(uri);
		final long length = file.length();

		if (handler == null) {
			return;
		}

		byte[] array = new byte[(int) length];
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			is.read(array);
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		getSupportActionBar().setSubtitle(file.getAbsolutePath());
		StringBuilder contentString = new StringBuilder("");
		contentString.append("<html><head><title></title>");
		contentString
				.append("<link href=\"file:///android_asset/prettify.css\" rel=\"stylesheet\" type=\"text/css\"/> ");
		contentString
				.append("<script src=\"file:///android_asset/prettify.js\" type=\"text/javascript\"></script> ");
		contentString.append(handler.getFileScriptFiles());
		contentString
				.append("</head><body onload=\"prettyPrint()\"><code class=\""
						+ handler.getFilePrettifyClass() + "\">");
		String sourceString = new String(array);
		contentString.append(handler.getFileFormattedString(sourceString));
		contentString.append("</code> </html> ");

		codeReader.loadDataWithBaseURL("file:///android_asset/",
				contentString.toString(), "text/html", "", "");
		
		selectedFile = uri;
		supportInvalidateOptionsMenu();
		updateRecentReading(uri.getPath());
	}
	
	private void updateRecentReading(String url){
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Columns.DATE_TIMESTAMP.getName(), Calendar.getInstance().getTimeInMillis());
		values.put(Columns.URL.getName(), url);
		values.put(Columns.STARRED.getName(), isStarred);
		cr.insert(ReadingHistoryContent.Wish.CONTENT_URI, values);
		
	}

	private DocumentHandler getHandlerByFileExtension(Uri uri) {
		File file = new File(uri.getPath());
		if (file.exists() && file.isFile()) {
			String extension = MimeTypeMap.getFileExtensionFromUrl(file
					.getAbsolutePath());
			if (extension.equals(".java")) {
				return new JavaDocumentHandler();
			} else if (extension.equals(".cpp")) {
				return new CppDocumentHandler();
			} else if (extension.equals("c")) {
				return new CDocumentHandler();
			} else if (extension.equals("html") || extension.equals("hml")
					|| extension.equals("xhtml")) {
				return new HtmlDocumentHandler();
			} else if (extension.equals("js")) {
				return new JavascriptDocumentHandler();
			} else if (extension.equals(".mxml")) {
				return new MxmlDocumentHandler();
			} else if (extension.equals(".pl")) {
				return new PerlDocumentHandler();
			} else if (extension.equals(".py")) {
				return new PythonDocumentHandler();
			} else if (extension.equals(".rb")) {
				return new RubyDocumentHandler();
			} else if (extension.equals(".xml")) {
				return new XmlDocumentHandler();
			} else if (extension.equals(".css")) {
				return new CssDocumentHandler();
			} else if (extension.equals(".el") || extension.equals(".lisp")
					|| extension.equals(".scm")) {
				return new LispDocumentHandler();
			} else if (extension.equals(".lua")) {
				return new LuaDocumentHandler();
			} else if (extension.equals(".ml")) {
				return new MlDocumentHandler();
			} else if (extension.equals(".vb")) {
				return new VbDocumentHandler();
			} else if (extension.equals(".sql")) {
				return new SqlDocumentHandler();
			}
			return new TextDocumentHandler();
		}
		return null;

	}

}
