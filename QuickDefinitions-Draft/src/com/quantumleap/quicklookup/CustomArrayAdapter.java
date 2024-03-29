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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class CustomArrayAdapter extends ArrayAdapter<String> implements Filterable {
	
	private List<String> list;
	private CustomFilter customFilter;
	private static int maxLimit = -1;

	public CustomArrayAdapter(Context context, int textViewResourceId, int maxEntries) {
		super(context, textViewResourceId);
		list = new ArrayList<String>();
		maxLimit = maxEntries;
	}

	@Override
	public void add(String object) {
		
		if( maxLimit > 0) {
			
			if(list.contains(object))
				return;
			
			if(maxLimit != -1 && list.size() == maxLimit) {
				list.remove(0);
			}
			
			list.add(object);
			notifyDataSetChanged();
		}
	}

	@Override
	public void clear() {
		list.clear();
	}
	
	public void updateAll(Set<String> stringSet) {
		
		for (String s : stringSet) {  
		    list.add(s);  
		}  
	}
	
	public List<String> getStringList() {
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public Filter getFilter() {
		if (customFilter == null) {
			customFilter = new CustomFilter();
		}
		return customFilter;
	}

	public void callFiltering(String term) {
		customFilter.performFiltering(term);
	}

	private class CustomFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null) {
				results.values = list;
				results.count = list.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results != null && results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}

}