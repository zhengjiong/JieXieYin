package org.namofo.fragment;

import org.namofo.R;
import org.namofo.adapter.ForumAdapter;
import org.namofo.fragment.forum.ForumListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 论坛
 * @author 
 * 2014-6-19 上午8:28:45
 */
public class ForumFragment extends BaseFragment{

	private ListView mListView;
	
	private String[] forumArr = {"精华推荐", "新人专区", "戒邪淫区", "学佛专区", "会员生活区 "};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.forum_layout, null);
		mListView = (ListView) view.findViewById(R.id.forum_listView_id);
		initData() ;
		return view;
	}
	
	
	
	@Override
	protected void initView() {
	}

	@Override
	protected void initVariable() {
		
	}

	@Override
	protected void initData() {
		mListView.setAdapter(new ForumAdapter(getActivity(), forumArr));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				System.out.println("pos "+pos);
				
				Intent intent = new Intent(getActivity(), ForumListActivity.class);
				intent.putExtra("forumId", pos);
				intent.putExtra("forumText", forumArr[pos]);
				startActivity(intent);
				
			}
		});
	}

	@Override
	protected void initListener() {
		
	}

}
