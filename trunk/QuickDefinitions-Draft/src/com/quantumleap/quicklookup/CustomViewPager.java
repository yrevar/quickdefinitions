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

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomViewPager extends ViewPager {

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// we some the listner
	protected OnPageChangeListener listener;

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		super.setOnPageChangeListener(listener);
		this.listener = listener;
	}

	@Override
	public void setCurrentItem(int item) {
		// when you pass set current item to 0,
		// the listener won't be called so we call it on our own
		boolean invokeMeLater = false;

		if(super.getCurrentItem() == 0 && item == 0)
			invokeMeLater = true;

		super.setCurrentItem(item);

		if(invokeMeLater)
			listener.onPageSelected(0);
	}

}