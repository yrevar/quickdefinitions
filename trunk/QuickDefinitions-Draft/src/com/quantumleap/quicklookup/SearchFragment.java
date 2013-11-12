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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchFragment extends Fragment {

	public static final String KEY_PAGE_NUMBER = "KEY_PAGE_NUMBER";
	private int myPageNumber;
	
	public static final SearchFragment newInstance(int pageNo)
	{
		SearchFragment f = new SearchFragment();
		Bundle bdl = new Bundle(1);
		bdl.putInt(KEY_PAGE_NUMBER, pageNo);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		myPageNumber = getArguments().getInt(KEY_PAGE_NUMBER);

		View v = inflater.inflate(R.layout.search_fragement_google_search, container, false);
		TextView messageTextView = (TextView)v.findViewById(R.id.search_type_text);
		messageTextView.setText(MainActivity.getSearchTypeNameFromPageIndex(myPageNumber));
		
		if(myPageNumber == 0) {
			ImageView iv = (ImageView) v.findViewById(R.id.search_hint_arrow_left);
			iv.setVisibility(View.GONE);
		}
		else if(myPageNumber == 2) {
			ImageView iv = (ImageView) v.findViewById(R.id.search_hint_arrow_right);
			iv.setVisibility(View.GONE);
		}
		
		//Handle search button click event
		ImageButton ib = (ImageButton) v.findViewById(R.id.search_button_image);
		ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				((MainActivity)getActivity()).SearchResults();
			}
		});
		
		return v;
	}
}
