package org.namofo.util;

import android.util.SparseArray;
import android.view.View;

/**
 * 
 * @author zhengjiong
 * @version 1.0
 * @created 2014-7-4 上午10:48:46
 */
public class ViewHolderUtils {
	
	public static <T extends View> T get(View v, int id){
		SparseArray<View> holder = (SparseArray<View>) v.getTag();
		
		if (holder == null) {
			holder = new SparseArray<View>();
			v.setTag(holder);
		}
		View childView = holder.get(id);
		if (childView == null) {
			childView = v.findViewById(id);
			holder.put(id, childView);
		}
		return (T) childView;
	}
}
