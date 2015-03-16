package org.namofo.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.HttpUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.namofo.constants.Constants;
import org.namofo.sqlite.dao.DaoMaster;
import org.namofo.sqlite.dao.DaoMaster.OpenHelper;
import org.namofo.sqlite.dao.DaoSession;
import org.namofo.sqlite.service.ArticleCategoryDBService;
import org.namofo.sqlite.service.ArticleDbService;
import org.namofo.util.DBUtil;

import java.io.IOException;

/**
 * 
 * @author zhengjiong
 * 2014-6-18 上午6:30:56
 */
public class AppContext extends Application{
	private static AppContext mAppContext;

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;

	private static ArticleCategoryDBService articleCategoryService;
	private static ArticleDbService articleDBService;

    private HttpUtils mHttpUtils;

    private static ImageLoader mImageLoader;

    public static AppContext getInstance() {
        return mAppContext;
    }

    public HttpUtils getHttpUtils() {
        return mHttpUtils;
    }

	@Override
	public void onCreate() {
		super.onCreate();
		mAppContext = this;
        mHttpUtils = new HttpUtils();

        initImageLoader();

        try {
			DBUtil.copyDataBase(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
    }

    /**
	 * 獲取DaoMaster
	 * @param context
	 * @return DaoMaster
	 */
	public static DaoMaster getDaoMaster(Context context){
		if (daoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}
	
	/**
	 * 獲取DaoSession
	 * @param context
	 * @return DaoSession
	 */
	public static DaoSession getDaoSession(Context context){
		if (daoSession == null) {
			daoMaster = getDaoMaster(context);
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
	
	/**
	 * 獲取文章分類Service
	 * @param context
	 * @return
	 */
	public static ArticleCategoryDBService getArticleCategoryDBService(Context context){
		if (articleCategoryService == null) {
			synchronized (ArticleCategoryDBService.class) {
				if (articleCategoryService == null) {
					articleCategoryService = new ArticleCategoryDBService(getDaoSession(context).getArticleCategoryDao());
				}
			}
		}
		return articleCategoryService;
	}
	
	/**
	 * 获取文章service
	 * @param context
	 * @return
	 */
	public static ArticleDbService getArticleDBService(Context context){
		if (articleDBService == null) {
			synchronized (ArticleDbService.class) {
				if (articleDBService == null) {
					articleDBService = new ArticleDbService(getDaoSession(context).getArticleDao());
				}
			}
		}
		
		return articleDBService;
	}


    /**
     * 显示图片的配置
     * @param defaultImgResId
     * @return
     */
    public static DisplayImageOptions getImageOptions(int defaultImgResId){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultImgResId)
                .showImageOnLoading(defaultImgResId)
                .showImageOnFail(defaultImgResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        return options;
    }

    public static ImageLoader getmImageLoader() {
        return mImageLoader;
    }
}
