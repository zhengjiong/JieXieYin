package org.namofo.adapter;

import org.namofo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Administrator
 *
 */
public class ForumAdapter extends BaseAdapter{
	private String[] forumArr;
	private Context mContext;
	
	public ForumAdapter(Context context, String[] forumArr){
		mContext = context;
		this.forumArr = forumArr;
	}

	@Override
	public int getCount() {
		return forumArr.length;
	}

	@Override
	public Object getItem(int position) {
		return forumArr[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class Holder{
		private TextView forumText;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
				
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.forum_item, null);
			holder = new Holder();
			holder.forumText = (TextView) convertView.findViewById(R.id.forum_type_id);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		holder.forumText.setText(forumArr[position]);
		return convertView;
	}
}
