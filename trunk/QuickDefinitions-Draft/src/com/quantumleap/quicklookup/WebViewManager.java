 /*
  * <QuickLookup: quickest way to find definitions, web results and images>
    Copyright (©) <2013>  <Soma Chakraborty, Yagnesh Revar>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.quantumleap.quicklookup;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewManager {
	
	public enum SearchType {

		SEARCH_TYPE_GOOGLE(0), SEARCH_TYPE_GOOGLE_DEFINITIONS(1), SEARCH_TYPE_GOOGLE_IMAGES(2);

		final int numTab;

		private SearchType(int num){
			this.numTab = num;
		}

		public int getValue(){
			return this.numTab;
		}

	};

	//private final static String URL_KEY = "QL_WEB_VIEW_URL_KEY";
	//private final static String SEARCH_TYPE_KEY = "QL_WEB_VIEW_SEARCH_TYPE_KEY";
	private static final String TAG = "QLWebViewManager";
	private WebView _webView = null;
	private final static String GOOGLE_DEFINITIONS_PREFIX = "https://www.google.com/search?q=define+";
	private final static String GOOGLE_SEARCH_PREFIX = "https://www.google.com/search?q=";
	private final static String GOOGLE_IMAGES_PREFIX = "https://www.google.com/search?tbm=isch&q=";
	//private String lastQuery = "Invalid Query";
	//private SearchType lastSearchType = SearchType.SEARCH_TYPE_GOOGLE;



	public WebViewManager(WebView webView) {

		Log.d(TAG, "WebView Created");
		_webView = webView;
		//Enable javascript in webview
		_webView.getSettings().setJavaScriptEnabled(true);

		_webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url){
				
				// do your handling codes here, which url is the requested url
				// probably you need to open that url rather than redirect:

				Log.d(TAG, "WebView Load Redirect  Url");
				_webView.loadUrl(url);
				return false; // then it is not handled by default action
			}
		});
		LoadWebView(SearchType.SEARCH_TYPE_GOOGLE, "");
	}

	public void LoadWebView(SearchType searchType, String query) {

		String url;
		
		Log.d(TAG, "WebView Load Url");

		switch(searchType) {

		case SEARCH_TYPE_GOOGLE_DEFINITIONS:
			//Construct definitions URL from received word read from bundle
			url = GOOGLE_DEFINITIONS_PREFIX + query;
			break;

		case SEARCH_TYPE_GOOGLE_IMAGES:
			//Construct definitions URL from received word read from bundle
			url = GOOGLE_IMAGES_PREFIX + query;
			break;

		default:
		case SEARCH_TYPE_GOOGLE:
			//Construct definitions URL from received word read from bundle
			url = GOOGLE_SEARCH_PREFIX + query;
			break;
		}
		
		//lastQuery = query;
		//lastSearchType = searchType;
		_webView.loadUrl(url);
	}
}
