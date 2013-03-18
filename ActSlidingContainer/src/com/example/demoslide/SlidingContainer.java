package com.example.demoslide;
import java.util.ArrayList;  

import android.content.Context;  
import android.content.res.TypedArray;  
import android.graphics.Canvas;  
import android.graphics.Rect;  
import android.util.AttributeSet;  
import android.util.Log;
import android.view.MotionEvent;  
import android.view.View;  
import android.view.ViewConfiguration;  
import android.view.ViewGroup;  
import android.widget.Scroller;  
  
/** 
 * @author Sodino E-mail:sodinoopen@hotmail.com 
 * @version Time��2012-1-18 ����02:55:59 
 */  
public class SlidingContainer extends ViewGroup {  
    private static final int INVALID_SCREEN = -1;  
    public static final int SCROLL_DURATION = 500;  
    public static final int SPEC_UNDEFINED = ViewGroup.LayoutParams.FILL_PARENT;  
    public static final int SNAP_VELOCITY = 500;  
    private static final int STATE_STATIC = 0;  
    private static final int STATE_SCROLLING = 1;  
    private int pageWidth;  
    /** 
     * ��ʶ�Ƿ��ǵ�һ�β��֡�<br/> 
     * ��һ�β�����Ҫ����һҳ��������ʾ����Ļ�ϡ�<br/> 
     */  
    private boolean isFirstLayout;  
    private int currentPage, nextPage;  
    private Scroller scroller;  
    /** ��ָ���������п����Ϊ�϶�����С���ȡ� */  
    private int distanceSlop;  
    private int state = STATE_STATIC;  
    private float lastMotionX;  
    private OnSlidingListener slidingListener;  
  
    public SlidingContainer(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        Log.v("123", "SlidingContainer() 3");  
        initialization(context, attrs);  
    }  
  
    public SlidingContainer(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        Log.v("123", "SlidingContainer() 2");  
        initialization(context, attrs);  
    }  
  
    public SlidingContainer(Context context) {  
        super(context);  
        Log.v("123", "SlidingContainer() 1");  
        initialization(context, null);  
    }  
  
    private void initialization(Context context, AttributeSet attrs) {  
        if (attrs != null) {  
            TypedArray typedArr = context.obtainStyledAttributes(attrs, R.styleable.sliding_SlidingContainer);  
            pageWidth = typedArr.getDimensionPixelSize(R.styleable.sliding_SlidingContainer_pageWidth, SPEC_UNDEFINED);  
            typedArr.recycle();  
        }  
  
        state = STATE_STATIC;  
        isFirstLayout = true;  
        currentPage = 0;  
        nextPage = INVALID_SCREEN;  
  
        scroller = new Scroller(context);  
  
        final ViewConfiguration configuration = ViewConfiguration.get(context);  
        distanceSlop = configuration.getScaledTouchSlop();  
    }  
  
    public int getCurrentPage() {  
        return currentPage;  
    }  
  
    public int getScrollXByPage(int page) {  
        return (page * pageWidth) - getPagePadding();  
    }  
  
    public int getPagePadding() {  
        return (getMeasuredWidth() - pageWidth) >> 1;  
    }  
  
    public int getPageWidth() {  
        return pageWidth;  
    }  
  
    public boolean scroll2page(int page) {  
        if (page < 0) {  
            return false;  
        } else if (page >= getChildCount()) {  
            return false;  
        } else if (scroller.isFinished() == false) {  
            return false;  
        }  
        enableChildrenCache(true);  
        boolean changingPage = (page != currentPage);  
        nextPage = page;  
  
        View focusedChild = getFocusedChild();  
        if (changingPage && focusedChild != null && focusedChild == getChildAt(currentPage)) {  
            focusedChild.clearFocus();  
        }  
  
        final int nowX = getScrollX();  
        final int newX = getScrollXByPage(nextPage);  
        final int move = newX - nowX;  
        final int absMove = Math.abs(move);  
        int duration = SCROLL_DURATION;  
        if (absMove < pageWidth) {  
            duration = SCROLL_DURATION * absMove / pageWidth;  
        }  
        // ����������������   
        scroller.startScroll(nowX, 0, move, 0, duration);  
        invalidate();  
        return true;  
    }  
  
    private void checkScrolling(float x) {  
        float diff = Math.abs(x - lastMotionX);  
        if (diff > distanceSlop) {  
            state = STATE_SCROLLING;  
            enableChildrenCache(true);  
        }  
    }  
  
    /** 
     * ��ʼ����ʱ��������ʹ�û��档<br/> 
     * ��������ʱ����ȡ�����档<br/> 
     */  
    public void enableChildrenCache(boolean enable) {  
        setChildrenDrawingCacheEnabled(enable);  
        setChildrenDrawnWithCacheEnabled(enable);  
    }  
  
    /** ����ʽ��ʾ֮ǰ���ò���Ч�� */  
    public boolean setPageWidth(int width) {  
        if (isFirstLayout) {  
            pageWidth = width;  
            return true;  
        }  
        return false;  
    }  
  
    public void setOnSlidingListener(OnSlidingListener listener) {  
        slidingListener = listener;  
    }  
  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        Log.v("123", "onMeasure()");  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
  
        pageWidth = (pageWidth == SPEC_UNDEFINED) ? getMeasuredWidth() : pageWidth;  
        pageWidth = Math.min(Math.max(0, pageWidth), getMeasuredWidth());  
  
        final int count = getChildCount();  
        for (int i = 0; i < count; i++) {  
            int childWidthSpec = MeasureSpec.makeMeasureSpec(pageWidth, MeasureSpec.EXACTLY);  
            View view = getChildAt(i);  
            view.measure(childWidthSpec, heightMeasureSpec);  
        }  
    }  
  
    @Override  
    protected void onLayout(boolean changing, int left, int top, int right, int bottom) {  
        Log.v("123", "onLayout");  
        int childLeft = 0;  
        final int count = getChildCount();  
        for (int i = 0; i < count; i++) {  
            final View view = getChildAt(i);  
            if (view.getVisibility() != View.GONE) {  
                int childWidth = view.getMeasuredWidth();  
                view.layout(childLeft, 0, childLeft + childWidth, view.getMeasuredHeight());  
                childLeft += childWidth;  
            }  
        }  
  
        if (isFirstLayout) {  
            scrollTo(getScrollXByPage(currentPage), 0);  
            isFirstLayout = false;  
        }  
    }  
  
    public boolean onInterceptTouchEvent(MotionEvent event) {  
        Log.v("123", "onInterceptTouchEvent action=" + event.getAction());  
        final int action = event.getAction();  
        if (action == MotionEvent.ACTION_MOVE && state != STATE_STATIC) {  
            // MOVE���Ǿ�ֹ����£�����TRUE��ֹ�����¼����ݸ��������   
            // ����ִ��onTouchEvent()��ʵ�ֻ���   
            return true;  
        }  
        final float x = event.getX();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            lastMotionX = x;  
            // �����ťʱ���˴�����״̬Ϊ��ֹ��   
            state = scroller.isFinished() ? STATE_STATIC : STATE_SCROLLING;  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if (state == STATE_STATIC) {  
                // �����Ѿ�ֹ���ڵ����ť��������������������λ�ƴ�С�����Ƿ���Ҫ�ı�״̬������һ�����ش��¼���   
                checkScrolling(x);  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
        case MotionEvent.ACTION_CANCEL:  
            enableChildrenCache(false);  
            state = STATE_STATIC;  
            break;  
        }  
        // �Ǿ�ֹ״̬�������¼�����onTouchEvent()����   
        return state != STATE_STATIC;  
    }  
  
    public boolean onTouchEvent(MotionEvent event) {  
        Log.v("123", "onTouchEvent");  
        super.onTouchEvent(event);  
        final int action = event.getAction();  
        final float x = event.getX();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            lastMotionX = x;  
            if (scroller.isFinished() == false) {  
                scroller.abortAnimation();  
            }  
            break;  
        case MotionEvent.ACTION_MOVE:  
            if (state == STATE_STATIC) {  
                checkScrolling(x);  
            } else if (state == STATE_SCROLLING) {  
                int moveX = (int) (lastMotionX - x);  
                lastMotionX = x;  
                if (getScrollX() < 0 || getScrollX() > getChildAt(getChildCount() - 1).getLeft()) {  
                    // ����Խ�����������λ�Ƽ��롣   
                    moveX = moveX >> 1;  
                }  
                scrollBy(moveX, 0);  
            }  
            break;  
        case MotionEvent.ACTION_UP:  
        case MotionEvent.ACTION_CANCEL:  
            if (state == STATE_SCROLLING) {  
                final int startX = getScrollXByPage(currentPage);  
                // Ĭ��ѡ��ص���ָ����֮ǰ�ĵ�ǰҳ   
                int whichPage = currentPage;  
                int xSpace = getWidth() / 8;  
                if (getScrollX() < startX - xSpace) {  
                    whichPage = Math.max(0, whichPage - 1);  
                } else if (getScrollX() > startX + xSpace) {  
                    whichPage = Math.min(getChildCount() - 1, whichPage + 1);  
                }  
                scroll2page(whichPage);  
            }  
            state = STATE_STATIC;  
            break;  
        }  
        return true;  
    }  
  
    /** �����������������н������˳���� */  
    protected void dispatchDraw(Canvas canvas) {  
        final long drawingTime = getDrawingTime();  
  
        final int count = getChildCount();  
        for (int i = 0; i < count; i++) {  
            drawChild(canvas, getChildAt(i), drawingTime);  
        }  
  
        if (slidingListener != null) {  
            int adjustedScrollX = getScrollX() + getPagePadding();  
            slidingListener.onSliding(adjustedScrollX);  
            if (adjustedScrollX % pageWidth == 0) {  
                slidingListener.onSlidingEnd(adjustedScrollX / pageWidth, adjustedScrollX);  
            }  
        }  
    }  
  
    /** ��Scroller��ƥ�䣬ʵ�ֶ���Ч����ÿһ֡�Ľ�����¡� */  
    public void computeScroll() {  
        if (scroller.computeScrollOffset()) {  
            scrollTo(scroller.getCurrX(), scroller.getCurrY());  
            postInvalidate();  
        } else if (nextPage != INVALID_SCREEN) {  
            currentPage = nextPage;  
            nextPage = INVALID_SCREEN;  
            enableChildrenCache(false);  
        }  
    }  
  
  
    public static interface OnSlidingListener {  
        public void onSliding(int scrollX);  
  
        public void onSlidingEnd(int pageIdx, int scrollX);  
    }  
}  