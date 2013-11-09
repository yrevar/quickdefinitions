package com.google.code.quickdefinitions;

import com.example.easydefinitions.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
 
public class WebViewActivity extends Activity {
 
	private WebView webView;
	public final static String URL_KEY = "QL_WEB_VIEW_URL_KEY";
	
	private final static String GOOGLE_DEFINITIONS_PREFIX = "https://www.google.com/search?q=define+";
	private final static String DEFAULT_URL = GOOGLE_DEFINITIONS_PREFIX + "love";
	
	private String current_url = DEFAULT_URL;
 
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	
		String received_word = getIntent().getExtras().getString(URL_KEY);
		
		if(received_word != null) {
			
			//Construct definitions URL from received word read from bundle
			current_url = GOOGLE_DEFINITIONS_PREFIX + received_word;
		}

		// Set current activity's as webview  
		setContentView(R.layout.webview);
 
		//Find WebView object for changing properties
		webView = (WebView) findViewById(R.id.webView1);
		
		//Enable javascript in webview
		webView.getSettings().setJavaScriptEnabled(true);

		//Set webview URL
		webView.loadUrl(current_url);
		
		webView.setWebViewClient(new WebViewClient() {
		    public boolean shouldOverrideUrlLoading(WebView view, String url){
		        // do your handling codes here, which url is the requested url
		        // probably you need to open that url rather than redirect:
		    	
		    	webView.loadUrl(url);
		        return false; // then it is not handled by default action
		   }
		});
		
		this.setFinishOnTouchOutside(true);
	}
 
}