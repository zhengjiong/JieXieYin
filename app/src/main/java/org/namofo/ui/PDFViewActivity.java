package org.namofo.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;

import org.namofo.R;
import org.namofo.bean.Article;

/**
 * @Author: zhengjiong
 * Date: 2014-07-12
 * Time: 16:01
 */
public class PDFViewActivity extends BaseActivity{
    private Article mArticle;

    private PDFView mPdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        initVariable();
        initView();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        initActionBar();
        setActionBarTitle(mArticle.getTitle());

        mPdfView = (PDFView) findViewById(R.id.pdfview);
    }

    @Override
    protected void initVariable() {
        mArticle = (Article) getIntent().getSerializableExtra("article");
}

    @Override
    protected void initData() {
        if (mArticle != null){
            PDFView.Configurator config = mPdfView
                    .fromAsset(mArticle.getFile_path())
                    .enableSwipe(true);

            /*if (mArticle.getStart_page() != 0 || mArticle.getEnd_page() != 0){
                int[] pages = new int[mArticle.getEnd_page() - mArticle.getStart_page() + 1];
                for (int i = 0; i < pages.length; i++){
                    pages[i] = mArticle.getStart_page()+i;
                }
                config.pages(pages);//要显示的页
                config.defaultPav ge(mArticle.getStart_page());//默认从第一页开始
            }else{
                //config.defaultPage(0);//默认从第一页开始
            }*/
            config.onLoad(onLoadCompleteListener);
            config.load();
        }
    }

    OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int i) {
            mPdfView.jumpTo(mArticle.getStart_page());
        }
    };

    @Override
    protected void initListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.scale_enter_anim, R.anim.translate_exit_anim);
    }
}
