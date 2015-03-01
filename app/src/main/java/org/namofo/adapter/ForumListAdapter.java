package org.namofo.adapter;

import java.util.List;

import org.namofo.R;
import org.namofo.bean.PostModel;

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
public class ForumListAdapter extends BaseAdapter{
	private List<PostModel> postList;
	private Context mContext;
	
	public ForumListAdapter(Context context, List<PostModel> postList){
		mContext = context;
		this.postList = postList;
	}

	@Override
	public int getCount() {
		return postList.size();
	}

	@Override
	public Object getItem(int position) {
		return postList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class Holder{
		private TextView title;
		private TextView content;
		private TextView author;
		private TextView comment;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		PostModel model = postList.get(position);
				
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.forum_list_item, null);
			holder = new Holder();
			
			holder.title = (TextView) convertView.findViewById(R.id.forum_title_id);
			holder.content = (TextView) convertView.findViewById(R.id.forum_content_id);
			
			holder.author = (TextView) convertView.findViewById(R.id.forum_author_id);
			holder.comment = (TextView) convertView.findViewById(R.id.forum_comment_id);
			
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.title.setText(model.getTitle());
		holder.content.setText(model.getContent());
		holder.author.setText(model.getAuthor());
		holder.comment.setText(model.getCount()+"");
		
		return convertView;
	}
}
