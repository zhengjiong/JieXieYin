package org.namofo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.namofo.R;
import org.namofo.bean.Article;
import org.namofo.util.DocUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 顯示Doc文章的內容
 * @author zj
 * Date 2014年7月13日
 * Time 15:06:47
 */
public class DocViewActivity extends BaseActivity {
    private static final String TAG = "DocViewActivity";
    private static final boolean DEBUG = true;

    private TextView mTxtContent;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initVariable();
        initView();
        initData();
    }

    @Override
    protected void initView() {
        initActionBar();
        setActionBarTitle(mArticle.getTitle());

        mTxtContent = (TextView) findViewById(R.id.article_detail_content);
    }

    @Override
    protected void initVariable() {
        mArticle = (Article) getIntent().getSerializableExtra("article");
    }

    private class OpenDcoAsyncTask extends AsyncTask<Article, Void, String>{

        @Override
        protected void onPreExecute() {
            showProgress("加载中...");
        }

        @Override
        protected String doInBackground(Article... params) {
            try {
                InputStream in = getAssets().open("doc/" + params[0].getFile_path());
                return DocUtils.getDocContent(in);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String content) {
            hideProgress();
            if (content != null){
                mTxtContent.setText(content);
            }
        }
    }

    @Override
    protected void initData() {
       new OpenDcoAsyncTask().execute(mArticle);
    }

    @Override
    protected void initListener() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.scale_enter_anim, R.anim.translate_exit_anim);
    }

    void debug(String msg){
        if (DEBUG){
            if (msg != null){
                Log.i(TAG, msg);
            }
        }
    }
}
