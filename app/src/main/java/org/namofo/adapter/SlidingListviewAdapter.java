package org.namofo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.namofo.R;

/**
 * 
 * @author zhengjiong
 * 2014-6-18 上午7:16:41
 */
public class SlidingListviewAdapter extends BaseAdapter{
	private String[] mItems;
	private int[] mIconRes;
	private Context mContext;
	
	public SlidingListviewAdapter(Context context, String[] items, int[] iconRes){
		this.mItems = items;
		this.mIconRes = iconRes;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mItems.length;
	}

	@Override
	public String getItem(int position) {
		return mItems[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class Holder{
		private TextView mTxtColumn;
		private ImageView mIcon;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
			holder = new Holder();
			
			holder.mTxtColumn = (TextView) convertView.findViewById(R.id.sliding_list_item_text);
			holder.mIcon = (ImageView) convertView.findViewById(R.id.sliding_list_item_icon);

			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.mTxtColumn.setText(mItems[position]);
		holder.mIcon.setImageResource(mIconRes[position]);
		
		return convertView;
	}
}
