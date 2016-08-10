package com.gxl.intelligentkitchen.ui.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.utils.ScreenUtils;


/**
 * Created by GXL on 2016/7/30 0030.
 */
public class ViewPaperIndicator extends ViewGroup implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int mChildrenSize;
    private int mChildIndex;
    private int mChildWidth;
    private int mParentWidth;
    private Paint mPaint;
    private Path mPath;
    private Scroller mScroller;
    private int mLastX;
    private VelocityTracker mVelocityTracker;
    private int mStartX;
    private int mItemsCountOnScreen;
    private int mDefaultItemsCountOnScreen = 4;
    private int mFocusTextColor;
    private int mUnFocusTextColor;
    private int mDefaultFocusTextColor = Color.RED;
    private int mDefaultUnFocusTextColor = Color.GRAY;
    private int mLastXIntercept;
    private int mTextSize;
    private int mDefaultTextSize = 16;

    public void setmOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    private OnPageChangeListener mOnPageChangeListener;

    public void setmTitles(String[] mTitles) {
        this.mTitles = mTitles;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        mViewPager.addOnPageChangeListener(this);
    }

    private String[] mTitles;

    /**
     * 在布局初始化调用
     *
     * @param context
     * @param attrs
     */
    public ViewPaperIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPaperIndicator);
        mItemsCountOnScreen = typedArray.getInt(R.styleable.ViewPaperIndicator_itemsCountOnScreen, mDefaultItemsCountOnScreen);
        mFocusTextColor = typedArray.getInt(R.styleable.ViewPaperIndicator_focusTextColor, mDefaultFocusTextColor);
        mUnFocusTextColor = typedArray.getInt(R.styleable.ViewPaperIndicator_unFocusTextColor, mDefaultUnFocusTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewPaperIndicator_textSize, (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, mDefaultTextSize, getResources().getDisplayMetrics()));
        Log.i("TAG", "ViewPaperIndicator: "+mTextSize);
        typedArray.recycle();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStrokeWidth(2);
        mPath = new Path();
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mStartX = 0;
    }

    /**
     * 在代码中初始化调用
     *
     * @param context
     */
    public ViewPaperIndicator(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentheight = MeasureSpec.getSize(heightMeasureSpec);
        int parentwidht = MeasureSpec.getSize(widthMeasureSpec);
        int childcount = mTitles.length;
        int childwidth = ScreenUtils.getScreenWidth(getContext()) / mItemsCountOnScreen;
        int parentwidth = Math.max(childwidth * childcount, parentwidht);
        for (int i = 0; i < mTitles.length; i++) {
            final int j = i;
            TextView textView = new TextView(getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(childwidth, parentheight);
            textView.setText(mTitles[i]);
//            textView.setTextSize(mTextSize);
            textView.setTextColor(mUnFocusTextColor);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(layoutParams);
            addView(textView);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                    resetTextViewColor();
                    highLightTextView(j);
                }
            });
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(parentwidth, parentheight);
        mChildrenSize = getChildCount();
        mChildWidth = getChildAt(0).getMeasuredWidth();
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastXIntercept;
                if (Math.abs(deltaX) > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastXIntercept = x;
        return intercepted;
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mFocusTextColor);
        }

    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mUnFocusTextColor);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mTitles.length; i++) {
            View view = getChildAt(i);
            view.layout(i * view.getMeasuredWidth(), 0, i * view.getMeasuredWidth() + view.getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath = new Path();
        mPath.moveTo(mStartX + mChildWidth / 2-mChildWidth/12, getMeasuredHeight());
        mPath.lineTo(mStartX + mChildWidth / 2 +mChildWidth/12, getMeasuredHeight());
        mPath.lineTo(mStartX + mChildWidth / 2 , getMeasuredHeight()-mChildWidth/16);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                if ((getScrollX() - deltaX) >= 0 && (getScrollX() - deltaX) <= (getMeasuredWidth() - ScreenUtils.getScreenWidth(getContext()))) {
                    scrollBy(-deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocityTracker = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocityTracker) > 50) {
                    if ((getScrollX() >= 0 && getScrollX() <= (getMeasuredWidth() - ScreenUtils.getScreenWidth(getContext())))) {
                        deltaX = (int) xVelocityTracker;
                        if (xVelocityTracker > 0) {
                            Log.i("getscorll", getScrollX() + "");
                            smoothScrollBy(-Math.min(deltaX, getScrollX()), 0);
                        } else {
                            Log.i("getscorll", getScrollX() + "");
                            smoothScrollBy(Math.min(Math.abs(deltaX), getMeasuredWidth() - ScreenUtils.getScreenWidth(getContext()) - getScrollX()), 0);
                        }
                    }
                }
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        resetTextViewColor();
        highLightTextView(position);
        mStartX = (int) (position * mChildWidth + positionOffset * mChildWidth);
        if (position < mTitles.length - mItemsCountOnScreen) {
            smoothScrollBy(mStartX - getScrollX(), 0);
        }
        requestLayout();
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void smoothScrollBy(int destX, int destY) {
        int scrollX = getScrollX();
        mScroller.startScroll(scrollX, 0, destX, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

}
