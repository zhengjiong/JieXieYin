package org.namofo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import org.namofo.R;
import org.namofo.bean.Article;
import org.namofo.util.FileUtils;

import java.lang.reflect.Field;

/**
 * 文章TXT-詳情
 * @author zj
 * Date 2014年7月13日
 * Time 19:07:11
 */
public class TxtViewActivity extends BaseActivity {
    private static final String TAG = "DocViewActivity";
    private static final boolean DEBUG = true;

    private View mRootView;
    private ImageView mImgHeaderView;
    private TextView mTxtContent;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initData();
    }

    @Override
    protected void initView() {
        mRootView = getLayoutInflater().inflate(R.layout.activity_article_detail, null);
        mImgHeaderView = (ImageView) getLayoutInflater().inflate(R.layout.fading_actionbar_header, null);
        mTxtContent = (TextView) mRootView.findViewById(R.id.article_detail_content);

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
        initActionBarShowLogo();

        setActionBarTitle(mArticle.getTitle());
    }

    @Override
    protected void initVariable() {
        mArticle = (Article) getIntent().getSerializableExtra("article");
    }

    @Override
    protected void initData() {
        new OpenTxtAsyncTask().execute(mArticle);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String content = msg.obj.toString();

        }
    };

    private class OpenTxtAsyncTask extends AsyncTask<Article, Void, String>{

        @Override
        protected void onPreExecute() {
            showProgress("加载中...");
        }

        @Override
        protected String doInBackground(Article... params) {
            return FileUtils.readFileFromAsset("txt/"+params[0].getFile_path(), TxtViewActivity.this);
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
