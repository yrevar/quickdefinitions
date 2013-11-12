package com.quantumleap.quicklookup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "QLMainActivity";
	private static final String SAVE_KEY_AUTO_CMPLT_TEXT_VIEW= "SAVE_KEY_AUTO_CMPLT_TEXT_VIEW";
	private static final int maxAutoCompleteHistory = 2;

	private WebViewManager.SearchType currentSearchType = WebViewManager.SearchType.SEARCH_TYPE_GOOGLE_DEFINITIONS; //TODO: null and update this on viewpager first fragement created	
	private AutoCompleteTextView autoCompleteTextView = null;
	private WebView collapsibleWebView = null;
	private WebViewManager webViewManager = null;
	private ViewPager searchViewPagerView = null;
	private boolean isSearchView = true;
	private CustomArrayAdapter customArrayadapter;

	private OnPageChangeListener mCustomPagerListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int newPageIndex) {
			// TODO Auto-generated method stub
			//Log.d("***","OnPageChangeListener position " + newPageIndex );
			currentSearchType = getSearchTypeFromPageIndex(newPageIndex);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		new SimpleEula(this).show();

		Log.d(TAG, "QuickLookup MainActivity Created");

		setContentView(R.layout.activity_main);

		customArrayadapter = new CustomArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, maxAutoCompleteHistory);
		retrievePersistData();

		//Setup ViewPager
		SearchPageAdapter _pageAdapter;
		//ViewPager searchViewPagerView;
		List<Fragment> fragments = getFragments();
		_pageAdapter = new SearchPageAdapter(getSupportFragmentManager(), fragments);
		searchViewPagerView =
				(ViewPager)findViewById(R.id.search_pager);
		searchViewPagerView.setOnPageChangeListener(mCustomPagerListener);
		searchViewPagerView.setAdapter(_pageAdapter);

		//WebView
		collapsibleWebView = (WebView)findViewById(R.id.collapsibleWebView);
		webViewManager = new WebViewManager(collapsibleWebView);

		//Handle Autocomplete TextView 
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.query_edit_text_view);
		autoCompleteTextView.setAdapter(customArrayadapter);
		autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d(TAG, "Search on AutoCmplt...");
				SearchResults();
			}
		});

		autoCompleteTextView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				Log.d(TAG, "Search on KeyENTER..." + event);
				if(event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)  {

					//Log.d(TAG, "On Editor action ENTER ...");
					SearchResults();
					return true;
				}

				return false;
			}
		});

		autoCompleteTextView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {

				if(arg1 == true) {
					displaySearchView();
				}
			}

		});
		//Finish activity if clicked on outside region
		//this.setFinishOnTouchOutside(true);
		//Start foreground service+
		startForegroundService();
	}

	private List<Fragment> getFragments() {

		List<Fragment> fList = new ArrayList<Fragment>();

		fList.add(SearchFragment.newInstance(0)); //page 0
		fList.add(SearchFragment.newInstance(1)); //page 1
		fList.add(SearchFragment.newInstance(2)); //page 2
		return fList;
	}

	public static String getSearchTypeNameFromPageIndex(int pageIndex) {

		switch (pageIndex) {
		case 0:
			return "Google Definition"; //TODO: From String values 
		default:
		case 1:
			return "Google Search";

		case 2:
			return "Google Images";
		}
	}

	public static WebViewManager.SearchType getSearchTypeFromPageIndex(int pageIndex) {

		switch (pageIndex) {
		case 0:
			return WebViewManager.SearchType.SEARCH_TYPE_GOOGLE_DEFINITIONS;

		default:
		case 1:
			return WebViewManager.SearchType.SEARCH_TYPE_GOOGLE;

		case 2:
			return WebViewManager.SearchType.SEARCH_TYPE_GOOGLE_IMAGES;
		}
	}


	private void startForegroundService() {

		//Start forground service if not already started at boot intent
		Intent service = new Intent(this, MainService.class);
		startService(service);
	}

	private void displaySearchView() {

		isSearchView = true;
		searchViewPagerView.setVisibility(View.VISIBLE);
		autoCompleteTextView.requestFocus();
		collapsibleWebView.setVisibility(View.GONE);
	}

	private void displaySearchResultView() {

		isSearchView = false;
		collapsibleWebView.setVisibility(View.VISIBLE);
		collapsibleWebView.requestFocus();
		searchViewPagerView.setVisibility(View.GONE);
	}

	public void SearchResults() {

		String query = ((TextView)findViewById(R.id.query_edit_text_view)).getText().toString();

		//Add query into search history
		customArrayadapter.add(query);

		if(query.length() != 0) {

			Log.d(TAG, "Launching WebView " + currentSearchType );
			displaySearchResultView();
			webViewManager.LoadWebView(currentSearchType, query);
		}
	}

	@Override
	protected void onStop() {

		//Log.d(TAG, "**** ON STOP  *** \n");
		goodBye();
		super.onStop();
		this.finish();
	}

	@Override
	protected void onPause() {

		//Log.d(TAG, "**** ON PAUSE *** \n");
		goodBye();
		super.onPause();
		this.finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if(isSearchView != true) {
			displaySearchView();
		}
		else {

			goodBye();
			super.onBackPressed();
			this.finish();
		}
	}

	@Override
	protected void onDestroy() {

		goodBye();
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = MotionEventCompat.getActionMasked(event);

		switch(action) {
		case (MotionEvent.ACTION_DOWN) :
			Log.d(TAG,"Action was DOWN");
		return true;
		case (MotionEvent.ACTION_MOVE) :
			Log.d(TAG,"Action was MOVE");
		return true;
		case (MotionEvent.ACTION_UP) :
			Log.d(TAG,"Action was UP");
		if(isSearchView != true) {
			displaySearchView();
		}
		else {
			goodBye();
			this.finish();
		}
		return true;
		case (MotionEvent.ACTION_CANCEL) :
			Log.d(TAG,"Action was CANCEL");
		return true;
		case (MotionEvent.ACTION_OUTSIDE) :
			Log.d(TAG,"Movement occurred outside bounds " + "of current screen element");
		return true;      
		default : 
			return super.onTouchEvent(event);
		}      
	}

	private void retrievePersistData() {

		Log.d(TAG,"retrievePersistData");
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		customArrayadapter.clear();
		Set<String> retrievedData = sharedPreferences.getStringSet(SAVE_KEY_AUTO_CMPLT_TEXT_VIEW, new HashSet<String>());
		customArrayadapter.updateAll(retrievedData);
	}
	private void persistData() {

		Log.d(TAG,"persistData");
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		// Writing data to SharedPreferences
		Editor editor = sharedPreferences.edit();
		editor.putStringSet(SAVE_KEY_AUTO_CMPLT_TEXT_VIEW, new HashSet<String>(customArrayadapter.getStringList()));
		editor.commit();
	}	

	private void goodBye() {

		persistData();
		if (collapsibleWebView != null)
		{
			//Find Main Layout for detaching webview on destroy
			RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
			if (mainLayout != null)
			{
				mainLayout.removeView(collapsibleWebView);
			}
			collapsibleWebView.destroy();
		}
	}
}
