package org.namofo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import org.namofo.R;
import org.namofo.app.AppContext;
import org.namofo.bean.Article;
import org.namofo.bean.ArticleCategory;
import org.namofo.constants.Constants;
import org.namofo.sqlite.service.ArticleDbService;
import org.namofo.ui.ArticleDetailActivity;
import org.namofo.ui.DocViewActivity;
import org.namofo.ui.HtmlViewActivity;
import org.namofo.ui.MainActivity;
import org.namofo.ui.PDFViewActivity;
import org.namofo.ui.TxtViewActivity;
import org.namofo.util.FileUtils;
import org.namofo.util.ProgressDialogUtils;
import org.namofo.util.SDCardUtils;
import org.namofo.util.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * 文章
 * @author zhengjiong
 * @version 1.0
 * @created 2014-7-4 下午5:24:54
 */
public class ArticleFragment extends BaseFragment {
	private static final boolean DEBUG = true;
	private static final String TAG = "ArticleFragment";

    private MainActivity mainActivity;

    private int mScrollY;
    private ColorDrawable mActionBarBackgroundDrawalbe;
    private ArticleCategory mCategory;
    private CardArrayAdapter mAdapter;
    private ArticleDbService mDbService;
    private ArrayList<Article> mArticles = new ArrayList<Article>();

    private View mRootView;
    private CardListView mListView;
    private View mHeaderView;
    private PullToRefreshLayout mPullToRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCategory = (ArticleCategory) getArguments().getSerializable("category");
		mRootView = inflater.inflate(R.layout.article_layout, null);
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        debug("onViewCreated " + mCategory.getName());

		initView();
		initVariable();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
        mainActivity = (MainActivity) getActivity();
		mListView = (CardListView) mRootView.findViewById(R.id.carddemo_list_colors);
        mPullToRefreshLayout = (PullToRefreshLayout) mRootView.findViewById(R.id.ptr_layout);

        ActionBarPullToRefresh.from(mActivity)
                .options(Options.create()
                        .scrollDistance(0.4f)
                        .build()
                )
                .allChildrenArePullable()
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullToRefreshLayout.setRefreshComplete();
                            }
                        },2000);
                    }
                })
                .setup(mPullToRefreshLayout);
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		mDbService = AppContext.getArticleDBService(getActivity());
	}

	@Override
	protected void initData() {
		mArticles = (ArrayList<Article>) mDbService.loadAllByCategory(mCategory.getId());
		initCards();
	}

	private void initCards() {
		//Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < mArticles.size(); i++) {
        	ArticleCard card = new ArticleCard(this.getActivity());
        	card.setArticle(mArticles.get(i), i);
            card.setBackgroundResourceId(R.drawable.list_card_selector);
            /*switch (i) {
                case 0:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
                    break;
                case 1:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color4);
                    break;
                case 2:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
                    break;
                case 3:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color2);
                    break;
                case 4:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color1);
                    break;
            }*/

            cards.add(card);
        }

        mAdapter = new CardArrayAdapter(getActivity(), cards);

        setScaleAdapter();
	}

	@Override
	protected void initListener() {
        mActionBarBackgroundDrawalbe = new ColorDrawable(getResources().getColor(R.color.actionbar_light_translucent));
        mainActivity.mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawalbe);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawalbe.setCallback(callback);
        }

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int newScrollY = getListViewScrollY();
                //Log.i("zj", "onscroll newScrollY=" + newScrollY);
                /**
                 * android4.4以下版本有bug,必須重新設置title,不然会直接让背景消失
                 */
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
                    mainActivity.getActionBar().setTitle(mainActivity.getActionBar().getTitle());
                }

                int headerHeight = (int) mainActivity.getResources().getDimension(R.dimen.abc_action_bar_default_height) * 3;

                float ratio = 1 - ((float) Math.min(Math.max(newScrollY, 0), headerHeight) / headerHeight);
                //Log.i("zj", "ratio=" + ratio);
                mainActivity.actionBarAlpha = (int) (ratio * 255) ;

                //Log.i("zj", "newScrollY=" + newScrollY + " ,ratio=" + ratio);

                /**
                 * android4.4以下的版本必須重新設置ActionBar的背景才能生效
                 */
                mActionBarBackgroundDrawalbe.setAlpha(mainActivity.actionBarAlpha);
                mainActivity.mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawalbe);

                //設置header透明度
                mainActivity.getHomePageFragment().mImgHeadview.getDrawable().setAlpha(mainActivity.actionBarAlpha);
                mainActivity.getHomePageFragment().mHeaderView.getBackground().setAlpha(mainActivity.actionBarAlpha);

                PagerSlidingTabStrip tab = mainActivity.getHomePageFragment().mTabs;
                tab.getBackground().setAlpha(mainActivity.actionBarAlpha);
                tab.setTextColor(Color.argb(mainActivity.actionBarAlpha, 255, 255, 255));
                tab.setIndicatorColor(Color.argb(mainActivity.actionBarAlpha, 3, 169, 244));

                if (mainActivity.getHomePageFragment().mTabChildViews.size() == 0) {
                    for (int i = 0; i < tab.tabCount; i++) {
                        View v = tab.tabsContainer.getChildAt(i);
                        mainActivity.getHomePageFragment().mTabChildViews.add(v);
                    }
                }
                /**
                 * tab背景顏色和點擊顏色
                 */
                for (int i = 0; i < mainActivity.getHomePageFragment().mTabChildViews.size(); i++) {
                    View v = mainActivity.getHomePageFragment().mTabChildViews.get(i);
                    Drawable tabBackgroundDrawable = v.getBackground();
                    tabBackgroundDrawable.setAlpha(mainActivity.actionBarAlpha);
                    v.setBackgroundDrawable(tabBackgroundDrawable);
                }

                /**
                 * 設置tab隱藏顯示
                 */
                if (ratio == 0) {
                    /*for (int i = 0; i < tab.tabCount; i++) {
                        View v = tab.tabsContainer.getChildAt(i);
                        v.setEnabled(false);
                    }*/
                    tab.setVisibility(View.GONE);
                }else {
                    /*for (int i = 0; i < tab.tabCount; i++) {
                        View v = tab.tabsContainer.getChildAt(i);
                        v.setEnabled(true);
                    }*/
                    tab.setVisibility(View.VISIBLE);
                }

                //tab.getBack
                /*for (int i=0; i < tab.getChildCount();i++){
                    View childHeaderView = null;
                    try {
                        childHeaderView = tab.getChildAt(i);

                        if (childHeaderView instanceof TextView){
                            childHeaderView.getBackground().setAlpha(newAlpha);

                            ((TextView)childHeaderView).setTextColor(Color.argb(newAlpha, 255, 255, 255));

                        }else if (childHeaderView instanceof ImageView){

                            ((ImageView) childHeaderView).getDrawable().setAlpha(newAlpha);


                        }else{
                            //childHeaderView.getBackground().setAlpha(newAlpha);
                        }

                    }catch (NullPointerException exception) {
                        //((ImageView) childHeaderView).getDrawable().setAlpha(newAlpha);
                        exception.printStackTrace();
                    }
                }*/

                /*if (newScrollY == mScrollY) {
                    return;
                }

                if (newScrollY > mScrollY) {
                    // Scrolling up
                    //hide();
                    Log.i("zj", "上滑");

                } else if (newScrollY < mScrollY) {
                    // Scrolling down
                    //show();
                    Log.i("zj", "下滑");
                }
                mScrollY = newScrollY;*/
            }
        });
    }

    private Drawable.Callback callback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            mainActivity.mActionBar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

    /**
     * 在ListView中，使用getChildAt(index)的取值，只能是当前可见区域（列表可滚动）的子项！
     * 即取值范围在 >= ListView.getFirstVisiblePosition() &&  <= ListView.getLastVisiblePosition();
     * 1）所以如果想获取前部的将会出现返回Null值空指针问题；
     * 2）getChildCount跟getCount获取的值将会不一样（数量多时）；
     * 3）如果使用了getChildAt(index).findViewById(...)设置值的话，滚动列表时值就会改变了。
     * 需要使用getFirstVisiblePosition()获得第一个可见的位置，再用当前的position-它,再用getChildAt取值！即getChildAt(position - ListView。getFirstVisiblePosition()).findViewById(...)去设置值
     */
    protected int getListViewScrollY() {
        View topChild = mListView.getChildAt(0);//滾動的時候第一個可以見到的item
        if (topChild == null) {//初始化的時候會出現==null的情況
            return 0;

        } else {
            /*Log.i("zj",
                    "getFirstVisiblePosition=" + mListView.getFirstVisiblePosition() +
                            " ,topChild.getHeight=" + topChild.getHeight() +
                            " ,topChild.gettop=" + topChild.getTop() +
                            " ,value=" + (mListView.getFirstVisiblePosition() * topChild.getHeight() - topChild.getTop()) +
                            " ,headerview.height=" + mHeaderView.getMeasuredHeight()

            );*/
            /*if (mListView.getFirstVisiblePosition() != 0) {
                return (mListView.getFirstVisiblePosition()-1) * topChild.getHeight() - topChild.getTop();
            } else {
                //return mListView.getFirstVisiblePosition() * topChild.getHeight() - topChild.getTop();
                return 0;
            }*/
            /*if (mListView.getFirstVisiblePosition() == 0) {
                return mListView.getFirstVisiblePosition() * topChild.getHeight() - topChild.getTop();
            } else {
                return mListView.getFirstVisiblePosition() * topChild.getHeight() - topChild.getTop() + 33;
            }*/
            return mListView.getFirstVisiblePosition() * mHeaderView.getMeasuredHeight() - topChild.getTop();
        }
   }

	/**
	 * Alpha animation
	 */
	private void setAlphaAdapter() {
		AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mAdapter);
		animCardArrayAdapter.setAbsListView(mListView);
		mListView.setExternalAdapter(animCardArrayAdapter, mAdapter);
		
	}
	
	/**
     * Bottom animation
     */
    private void setBottomAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mAdapter);
    }	
	
	/**
     * Scale animation
     */
    private void setScaleAdapter() {
        AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(mAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            mHeaderView = mainActivity.getLayoutInflater().inflate(R.layout.listview_header_padding, null);
            mListView.addHeaderView(mHeaderView);
            mListView.setExternalAdapter(animCardArrayAdapter, mAdapter);
        }
    }

	public class ArticleCard extends Card{
        private int position;
		private Article mArticle;
		private TextView mTxtTitle;
        private TextView mTxtFileType;
        private View mHeaderView;
		
		public Article getArticle() {
			return mArticle;
		}

		public void setArticle(Article mArticle, int position) {
			this.mArticle = mArticle;
            this.position = position;
		}

		public ArticleCard(Context context) {
            /**
             * 設置Item Layout
             */
			super(context, R.layout.article_list_item);
			init();
		}

        /**
         * 設置Item點擊事件
         */
		private void init(){
			setOnClickListener(new OnCardClickListener() {

				@Override
				public void onClick(Card card, View view) {
                    final Article article = ((ArticleCard)card).mArticle;

                    final Intent intent = new Intent();
                    intent.putExtra("article", article);
                    switch (article.getFile_type().intValue()){
                        case 1://txt文章
                            intent.setClass(getActivity(), TxtViewActivity.class);
                            break;
                        case 2://doc文章
                            intent.setClass(getActivity(), DocViewActivity.class);
                            break;
                        case 3://pdf文章
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("选择打开方法");
                            builder.setItems(new CharSequence[]{"使用手机自带PDF文档查看器", "程序内部打开"}, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            if (SDCardUtils.sdcardIsExist(mActivity)){
                                                new OpenPDFTask().execute(mArticle.getFile_path());
                                            }else{
                                                ToastUtils.show(mActivity, "没有安装SD卡,请选择其他打开方式");
                                                return;
                                            }
                                            break;
                                        case 1:
                                            /**
                                             * 程序內部打開pdf文件
                                             */
                                            Intent intent = new Intent();
                                            intent.putExtra("article", article);
                                            intent.setClass(getActivity(), PDFViewActivity.class);
                                            startActivity(intent);
                                            mActivity.overridePendingTransition(R.anim.translate_enter_anim, R.anim.scale_exit_anim);
                                            break;
                                    }
                                }
                            });
                            builder.show();

                            return;
                        case 4://内容存放在数据库的文章
                            intent.setClass(getActivity(), ArticleDetailActivity.class);
                            break;
                        case 5://html文章
                            intent.setClass(getActivity(), HtmlViewActivity.class);
                            break;
                    }
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.translate_enter_anim, R.anim.scale_exit_anim);
				}
			});
		}


        /**
         * 設置每一項Item的內容
         */
		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {
			mTxtTitle = (TextView) parent.findViewById(R.id.article_title);
            mTxtFileType = (TextView) parent.findViewById(R.id.article_file_type);

			mTxtTitle.setText(mArticle.getTitle());
            if (mArticle.getArticleFileType().getFile_name().equals("db")){
                mTxtFileType.setText("");
            }else{
                mTxtFileType.setText(mArticle.getArticleFileType().getFile_name());
            }
        }
	}

    /**
     * 複製pdf文件到sdcard,使用外部app打開文件
     */
    class OpenPDFTask extends AsyncTask<String,Void,Boolean>{
        private String pdfPath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtils.show(mActivity, "加载中...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                pdfPath = Constants.PDF_SAVE_PATH + params[0];
                return FileUtils.writeFile(Constants.PDF_SAVE_PATH + params[0], mActivity.getAssets().open(params[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean writeSuccess) {
            super.onPostExecute(writeSuccess);
            if (writeSuccess){
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(pdfPath)), "application/pdf");

                if (intent.resolveActivity(mActivity.getPackageManager()) != null){
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.translate_enter_anim, R.anim.scale_exit_anim);
                }else{
                    ToastUtils.show(mActivity, "未找到外部pdf打开程序");
                }

            }
            ProgressDialogUtils.hide();
        }
    }

    public static ArticleFragment newInstance(ArticleCategory category) {
        ArticleFragment fragment = new ArticleFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);

        fragment.setArguments(bundle);

        return fragment;
    }

    void debug(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }
}
