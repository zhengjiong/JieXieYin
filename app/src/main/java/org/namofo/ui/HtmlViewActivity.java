package org.namofo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import org.namofo.R;
import org.namofo.bean.Article;
import org.namofo.util.FileUtils;

import java.lang.reflect.Field;

/**
 * 顯示Html網頁
 * @Author: zhengjiong
 * Date: 2014-08-26
 * Time: 08:09
 */
public class HtmlViewActivity extends BaseActivity{
    private View mRootView;
    private ImageView mImgHeaderView;
    private TextView mTxtContent;
    private WebView mWebView;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        mRootView = getLayoutInflater().inflate(R.layout.activity_article_detail, null);
        mTxtContent = (TextView) mRootView.findViewById(R.id.article_detail_content);
        mImgHeaderView = (ImageView) getLayoutInflater().inflate(R.layout.fading_actionbar_header, null);

        initFadingActionBar();

    }

    private void initFadingActionBar(){
        int randomRes = (int) ((Math.random() * 25) + 1 );
        String headerImgName = "fo_" + randomRes;
        try {
            Class clazz = R.drawable.class;
            Field field = clazz.getField(headerImgName);
            int resId = field.getInt(null);
            mImgHeaderView.setImageResource(resId);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.color.actionbar_light_translucent)
                .headerView(mImgHeaderView)
                .contentView(mRootView);

        setContentView(helper.createView(this));
        helper.initActionBar(this);

        mActionBar = helper.getActionBar(this);

        initHomeAndBackButton();

        setActionBarTitle(mArticle.getTitle());
    }

    @Override
    protected void initVariable() {
        mArticle = (Article) getIntent().getSerializableExtra("article");
    }

    @Override
    protected void initData() {
        new OpenHtmlAsyncTask().execute(mArticle);
    }

    @Override
    protected void initListener() {

    }

    private class OpenHtmlAsyncTask extends AsyncTask<Article, Void, String>{

        @Override
        protected void onPreExecute() {
            showProgress("加载中...");
        }

        @Override
        protected String doInBackground(Article... params) {
            return FileUtils.readFileFromAsset("html/" + params[0].getFile_path(), HtmlViewActivity.this);
        }

        @Override
        protected void onPostExecute(String content) {
            hideProgress();
            if (content != null){
                //mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
                mTxtContent.setText(Html.fromHtml(content));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
