package org.namofo.ui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.namofo.R;

/**
 * @author <a href="http://www.hugofernandes.pt">Hugo Fernandes</a>
 */
public class ListeningScrollView extends ScrollView {

    private ActionBar mActionBar;
    private Drawable actionBarBackgroundDrawable;
    private int actionBarHeight;
    private RelativeLayout headerView;
    private ImageView mHeaderviewImg;
    private Context mContext;

    public ListeningScrollView(Context context) {
        super(context);
        mContext = context;
    }

    public ListeningScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ListeningScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("zj", "l=" + l + ",t=" + t + ",oldl=" + oldl + ",oldt=" + oldt + ",getScrollY=" + this.getScrollY() + ",getTop()=" + this.getTop() +
                ",getY=" + this.getY() + ",getBottom=" + this.getBottom());

        if (actionBarBackgroundDrawable != null) {
            //int headerHeight = headerView.getHeight() - actionBarHeight;
            //int headerHeight = headerView.getHeight();
            int headerHeight = (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height) * 3;

            //float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            float ratio = 1 - ((float) Math.min(Math.max(t, 0), headerHeight) / headerHeight);

            //float ratio =

            /*if (ratio < 0.02f){
                ratio = 0.02f;
            }*/
            int newAlpha = (int) (ratio * 255) ;

            //Log.i("zj", "ratio=" + (1 - ratio));

            actionBarBackgroundDrawable.setAlpha(newAlpha);
            ColorDrawable colorDrawable = new ColorDrawable(0xbe000000);
            colorDrawable.setAlpha(newAlpha);

            mActionBar.setBackgroundDrawable(actionBarBackgroundDrawable);


            for (int i=0; i < headerView.getChildCount();i++){
                View childHeaderView = null;
                try {
                    childHeaderView = headerView.getChildAt(i);



                    if (childHeaderView instanceof TextView){
                        childHeaderView.getBackground().setAlpha(newAlpha);


                        ((TextView)childHeaderView).setTextColor(Color.argb(newAlpha, 255, 255, 255));

                    }else if (childHeaderView instanceof ImageView){

                        ((ImageView) childHeaderView).getDrawable().setAlpha(newAlpha);


                    }else{
                        childHeaderView.getBackground().setAlpha(newAlpha);
                    }

                }catch (NullPointerException exception) {
                    //((ImageView) childHeaderView).getDrawable().setAlpha(newAlpha);
                    exception.printStackTrace();
                }
            }
            //mHeaderviewImg.getDrawable().setAlpha(newAlpha);
            headerView.getBackground().setAlpha(newAlpha);
        }
    }

    /**
     * Set the actionBar background drawable
     *
     * @param actionBarBackgroundDrawable ActionBar background when "solid"
     * @param drawableCallback            Use <code>null</code> if
     *                                    <code>Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1</code>
     */
    public void setActionBarBackgroundDrawable(Drawable actionBarBackgroundDrawable, Callback drawableCallback) {
        this.actionBarBackgroundDrawable = actionBarBackgroundDrawable;
        if (drawableCallback != null)
            actionBarBackgroundDrawable.setCallback(drawableCallback);
    }

    /**
     * Set the top view where the actionbar should be transparent
     *
     * @param transparentHeaderView
     */
    public void setTransparentHeaderView(RelativeLayout transparentHeaderView) {
        this.headerView = transparentHeaderView;
    }

    public void setHeaderviewImg(ImageView imageView){
        this.mHeaderviewImg = imageView;
    }

    /**
     * Set the actionBar height
     *
     * @param actionBarHeight
     */
    public void setActionBarHeight(int actionBarHeight) {
        this.actionBarHeight = actionBarHeight;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        // http://stackoverflow.com/a/6894270/244576
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return 0.0f;
        }
        return super.getBottomFadingEdgeStrength();
    }

    public void setActionBar(ActionBar actionBar) {
        this.mActionBar = actionBar;
    }
}
