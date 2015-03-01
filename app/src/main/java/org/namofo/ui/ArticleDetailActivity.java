package org.namofo.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import org.namofo.R;
import org.namofo.bean.Article;

import java.lang.reflect.Field;

/**
 * 文章詳情
 * @author zj
 * Date: 2014-7-13
 * Time: 11:41:52
 */
public class ArticleDetailActivity extends BaseActivity {
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
        initHomeAndBackButton();

        setActionBarTitle(mArticle.getTitle());
    }

    @Override
    protected void initVariable() {
        mArticle = (Article) getIntent().getSerializableExtra("article");
    }

    @Override
    protected void initData() {
        mTxtContent.setText(mArticle.getContent());
    }

    @Override
    protected void initListener() {}

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
