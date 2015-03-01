package org.namofo.fragment.forum;

import java.util.ArrayList;
import java.util.List;

import org.namofo.R;
import org.namofo.adapter.ForumListAdapter;
import org.namofo.bean.PostModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 论坛列表
 * @author Administrator
 *
 */
public class ForumListActivity extends Activity{
	
	private ListView mListView;
	
	private List<PostModel> postList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forumlist_layout);
		mListView = (ListView) findViewById(R.id.forumlist_listView_id);
		initView();
		initData() ;
	}

	
	
	private void initView() {
		findViewById(R.id.forum_back_id).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ForumListActivity.this.finish();
			}
		});
		
	}



	protected void initData() {
		postList = new ArrayList<PostModel>();
		PostModel model = null;
		for (int i = 0; i < 15; i++) {
			model = new PostModel(i, "增么提高自己的内功"+i,
					"1. 仁义礼智信； 2. 四种清净明晦 ； 3. 多读善书； 4. 多吃素，多放生，众善奉行，诸恶莫作。", 
					"善莫大焉", i);
			postList.add(model);
		}
		
		mListView.setAdapter(new ForumListAdapter(this, postList));
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				System.out.println("pos "+pos);
				
				Intent intent = new Intent(ForumListActivity.this, PostDetailActivity.class);
				intent.putExtra("postId", pos);
				intent.putExtra("postTitle", postList.get(pos).getTitle());
				startActivity(intent);
				
			}
		});
		TextView tv = (TextView) findViewById(R.id.forum_name_id);
		tv.setText(getIntent().getStringExtra("forumText"));
	}
	
	
}
