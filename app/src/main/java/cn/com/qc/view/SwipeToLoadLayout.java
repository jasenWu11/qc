package cn.com.qc.view;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;

import cn.com.qc.R;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;
import cn.com.qc.yinter.SwipeLoadMoreTrigger;
import cn.com.qc.yinter.SwipeRefreshTrigger;
import cn.com.qc.yinter.SwipeTrigger;

public class SwipeToLoadLayout extends ViewGroup {
    private static final String TAG = SwipeToLoadLayout.class.getSimpleName();
    private static final int DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION = 200;
    private static final int DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION = 200;
    private static final int DEFAULT_REFRESH_COMPLETE_DELAY_DURATION = 300;
    private static final int DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 500;
    private static final int DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION = 500;
    private static final int DEFAULT_SWIPING_TO_LOAD_MORE_TO_DEFAULT_SCROLLING_DURATION = 200;
    private static final int DEFAULT_RELEASE_TO_LOADING_MORE_SCROLLING_DURATION = 200;
    private static final int DEFAULT_LOAD_MORE_COMPLETE_DELAY_DURATION = 300;
    private static final int DEFAULT_LOAD_MORE_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 300;
    private static final int DEFAULT_DEFAULT_TO_LOADING_MORE_SCROLLING_DURATION = 300;
    private static final float DEFAULT_DRAG_RATIO = 0.5F;
    private static final int INVALID_POINTER = -1;
    private static final int INVALID_COORDINATE = -1;
    private SwipeToLoadLayout.AutoScroller mAutoScroller;
    private OnRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;
    private View mHeaderView;
    private View mTargetView;
    private View mFooterView;
    private int mHeaderHeight;
    private int mFooterHeight;
    private boolean mHasHeaderView;
    private boolean mHasFooterView;
    private boolean mDebug;
    private float mDragRatio;
    private boolean mAutoLoading;
    private final int mTouchSlop;
    private int mStatus;
    private int mHeaderOffset;
    private int mTargetOffset;
    private int mFooterOffset;
    private float mInitDownY;
    private float mInitDownX;
    private float mLastY;
    private float mLastX;
    private int mActivePointerId;
    private boolean mRefreshEnabled;
    private boolean mLoadMoreEnabled;
    private int mStyle;
    private float mRefreshTriggerOffset;
    private float mLoadMoreTriggerOffset;
    private float mRefreshFinalDragOffset;
    private float mLoadMoreFinalDragOffset;
    private int mSwipingToRefreshToDefaultScrollingDuration;
    private int mReleaseToRefreshToRefreshingScrollingDuration;
    private int mRefreshCompleteDelayDuration;
    private int mRefreshCompleteToDefaultScrollingDuration;
    private int mDefaultToRefreshingScrollingDuration;
    private int mReleaseToLoadMoreToLoadingMoreScrollingDuration;
    private int mLoadMoreCompleteDelayDuration;
    private int mLoadMoreCompleteToDefaultScrollingDuration;
    private int mSwipingToLoadMoreToDefaultScrollingDuration;
    private int mDefaultToLoadingMoreScrollingDuration;
    SwipeToLoadLayout.RefreshCallback mRefreshCallback;
    SwipeToLoadLayout.LoadMoreCallback mLoadMoreCallback;

    public SwipeToLoadLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDragRatio = 0.5F;
        this.mStatus = 0;
        this.mRefreshEnabled = true;
        this.mLoadMoreEnabled = true;
        this.mStyle = 0;
        this.mSwipingToRefreshToDefaultScrollingDuration = 200;
        this.mReleaseToRefreshToRefreshingScrollingDuration = 200;
        this.mRefreshCompleteDelayDuration = 300;
        this.mRefreshCompleteToDefaultScrollingDuration = 500;
        this.mDefaultToRefreshingScrollingDuration = 500;
        this.mReleaseToLoadMoreToLoadingMoreScrollingDuration = 200;
        this.mLoadMoreCompleteDelayDuration = 300;
        this.mLoadMoreCompleteToDefaultScrollingDuration = 300;
        this.mSwipingToLoadMoreToDefaultScrollingDuration = 200;
        this.mDefaultToLoadingMoreScrollingDuration = 300;
        this.mRefreshCallback = new SwipeToLoadLayout.RefreshCallback() {
            public void onPrepare() {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    SwipeToLoadLayout.this.mHeaderView.setVisibility(View.VISIBLE);
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onPrepare();
                }

            }

            public void onMove(int y, boolean isComplete, boolean automatic) {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isRefreshStatus(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mHeaderView.getVisibility() != View.VISIBLE) {
                        SwipeToLoadLayout.this.mHeaderView.setVisibility(View.VISIBLE);
                    }

                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onMove(y, isComplete, automatic);
                }

            }

            public void onRelease() {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isReleaseToRefresh(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onRelease();
                }

            }

            public void onRefresh() {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.STATUS.isRefreshing(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mHeaderView instanceof SwipeRefreshTrigger) {
                        ((SwipeRefreshTrigger) SwipeToLoadLayout.this.mHeaderView).onRefresh();
                    }

                    if (SwipeToLoadLayout.this.mRefreshListener != null) {
                        SwipeToLoadLayout.this.mRefreshListener.onRefresh();
                    }
                }

            }

            public void onComplete() {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onComplete();
                }

            }

            public void onReset() {
                if (SwipeToLoadLayout.this.mHeaderView != null && SwipeToLoadLayout.this.mHeaderView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mHeaderView).onReset();
                    SwipeToLoadLayout.this.mHeaderView.setVisibility(View.GONE);
                }

            }
        };
        this.mLoadMoreCallback = new SwipeToLoadLayout.LoadMoreCallback() {
            public void onPrepare() {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    SwipeToLoadLayout.this.mFooterView.setVisibility(View.VISIBLE);
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onPrepare();
                }

            }

            public void onMove(int y, boolean isComplete, boolean automatic) {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isLoadMoreStatus(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mFooterView.getVisibility() != View.VISIBLE) {
                        SwipeToLoadLayout.this.mFooterView.setVisibility(View.VISIBLE);
                    }

                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onMove(y, isComplete, automatic);
                }

            }

            public void onRelease() {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isReleaseToLoadMore(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onRelease();
                }

            }

            public void onLoadMore() {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.STATUS.isLoadingMore(SwipeToLoadLayout.this.mStatus)) {
                    if (SwipeToLoadLayout.this.mFooterView instanceof SwipeLoadMoreTrigger) {
                        ((SwipeLoadMoreTrigger) SwipeToLoadLayout.this.mFooterView).onLoadMore();
                    }

                    if (SwipeToLoadLayout.this.mLoadMoreListener != null) {
                        SwipeToLoadLayout.this.mLoadMoreListener.onLoadMore();
                    }
                }

            }

            public void onComplete() {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onComplete();
                }

            }

            public void onReset() {
                if (SwipeToLoadLayout.this.mFooterView != null && SwipeToLoadLayout.this.mFooterView instanceof SwipeTrigger && SwipeToLoadLayout.STATUS.isStatusDefault(SwipeToLoadLayout.this.mStatus)) {
                    ((SwipeTrigger) SwipeToLoadLayout.this.mFooterView).onReset();
                    SwipeToLoadLayout.this.mFooterView.setVisibility(View.GONE);
                }

            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeToLoadLayout, defStyleAttr, 0);

        try {
            int N = a.getIndexCount();

            for (int i = 0; i < N; ++i) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.SwipeToLoadLayout_refresh_enabled) {
                    this.setRefreshEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_enabled) {
                    this.setLoadMoreEnabled(a.getBoolean(attr, true));
                } else if (attr == R.styleable.SwipeToLoadLayout_swipe_style) {
                    this.setSwipeStyle(a.getInt(attr, 0));
                } else if (attr == R.styleable.SwipeToLoadLayout_drag_ratio) {
                    this.setDragRatio(a.getFloat(attr, 0.5F));
                } else if (attr == R.styleable.SwipeToLoadLayout_refresh_final_drag_offset) {
                    this.setRefreshFinalDragOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_final_drag_offset) {
                    this.setLoadMoreFinalDragOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.SwipeToLoadLayout_refresh_trigger_offset) {
                    this.setRefreshTriggerOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_trigger_offset) {
                    this.setLoadMoreTriggerOffset(a.getDimensionPixelOffset(attr, 0));
                } else if (attr == R.styleable.SwipeToLoadLayout_swiping_to_refresh_to_default_scrolling_duration) {
                    this.setSwipingToRefreshToDefaultScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.SwipeToLoadLayout_release_to_refreshing_scrolling_duration) {
                    this.setReleaseToRefreshingScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.SwipeToLoadLayout_refresh_complete_delay_duration) {
                    this.setRefreshCompleteDelayDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.SwipeToLoadLayout_refresh_complete_to_default_scrolling_duration) {
                    this.setRefreshCompleteToDefaultScrollingDuration(a.getInt(attr, 500));
                } else if (attr == R.styleable.SwipeToLoadLayout_default_to_refreshing_scrolling_duration) {
                    this.setDefaultToRefreshingScrollingDuration(a.getInt(attr, 500));
                } else if (attr == R.styleable.SwipeToLoadLayout_swiping_to_load_more_to_default_scrolling_duration) {
                    this.setSwipingToLoadMoreToDefaultScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.SwipeToLoadLayout_release_to_loading_more_scrolling_duration) {
                    this.setReleaseToLoadingMoreScrollingDuration(a.getInt(attr, 200));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_complete_delay_duration) {
                    this.setLoadMoreCompleteDelayDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.SwipeToLoadLayout_load_more_complete_to_default_scrolling_duration) {
                    this.setLoadMoreCompleteToDefaultScrollingDuration(a.getInt(attr, 300));
                } else if (attr == R.styleable.SwipeToLoadLayout_default_to_loading_more_scrolling_duration) {
                    this.setDefaultToLoadingMoreScrollingDuration(a.getInt(attr, 300));
                }
            }
        } finally {
            a.recycle();
        }

        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mAutoScroller = new SwipeToLoadLayout.AutoScroller();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        int childNum = this.getChildCount();
        if (childNum != 0) {
            if (0 < childNum && childNum < 4) {
                this.mHeaderView = this.findViewById(R.id.swipe_refresh_header);
                this.mTargetView = this.findViewById(R.id.swipe_target);
                this.mFooterView = this.findViewById(R.id.swipe_load_more_footer);
                if (this.mTargetView != null) {
                    if (this.mHeaderView != null && this.mHeaderView instanceof SwipeTrigger) {
                        this.mHeaderView.setVisibility(View.GONE);
                    }

                    if (this.mFooterView != null && this.mFooterView instanceof SwipeTrigger) {
                        this.mFooterView.setVisibility(View.GONE);
                    }

                }
            } else {
                throw new IllegalStateException("Children num must equal or less than 3");
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View footerView;
        MarginLayoutParams lp;
        if (this.mHeaderView != null) {
            footerView = this.mHeaderView;
            this.measureChildWithMargins(footerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            lp = (MarginLayoutParams) footerView.getLayoutParams();
            this.mHeaderHeight = footerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (this.mRefreshTriggerOffset < (float) this.mHeaderHeight) {
                this.mRefreshTriggerOffset = (float) this.mHeaderHeight;
            }
        }

        if (this.mTargetView != null) {
            footerView = this.mTargetView;
            this.measureChildWithMargins(footerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        if (this.mFooterView != null) {
            footerView = this.mFooterView;
            this.measureChildWithMargins(footerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            lp = (MarginLayoutParams) footerView.getLayoutParams();
            this.mFooterHeight = footerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (this.mLoadMoreTriggerOffset < (float) this.mFooterHeight) {
                this.mLoadMoreTriggerOffset = (float) this.mFooterHeight;
            }
        }

    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.layoutChildren();
        this.mHasHeaderView = this.mHeaderView != null;
        this.mHasFooterView = this.mFooterView != null;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new SwipeToLoadLayout.LayoutParams(-1, -1);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new SwipeToLoadLayout.LayoutParams(p);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SwipeToLoadLayout.LayoutParams(this.getContext(), attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case 1:
            case 3:
                this.onActivePointerUp();
            default:
                return super.dispatchTouchEvent(ev);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                this.mInitDownY = this.mLastY = this.getMotionEventY(event, this.mActivePointerId);
                this.mInitDownX = this.mLastX = this.getMotionEventX(event, this.mActivePointerId);
                if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus) || SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus)) {
                    this.mAutoScroller.abortIfRunning();
                    if (this.mDebug) {
                        Log.i(TAG, "Another finger down, abort auto scrolling, let the new finger handle");
                    }
                }

                if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus) || SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus)) {
                    return true;
                }
                break;
            case 1:
            case 3:
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId == -1) {
                    return false;
                }

                float y = this.getMotionEventY(event, this.mActivePointerId);
                float x = this.getMotionEventX(event, this.mActivePointerId);
                float yInitDiff = y - this.mInitDownY;
                float xInitDiff = x - this.mInitDownX;
                this.mLastY = y;
                this.mLastX = x;
                boolean moved = Math.abs(yInitDiff) > Math.abs(xInitDiff) && Math.abs(yInitDiff) > (float) this.mTouchSlop;
                boolean triggerCondition = yInitDiff > 0.0F && moved && this.onCheckCanRefresh() || yInitDiff < 0.0F && moved && this.onCheckCanLoadMore();
                if (triggerCondition) {
                    return true;
                }
            case 4:
            case 5:
            default:
                break;
            case 6:
                this.onSecondaryPointerUp(event);
                this.mInitDownY = this.mLastY = this.getMotionEventY(event, this.mActivePointerId);
                this.mInitDownX = this.mLastX = this.getMotionEventX(event, this.mActivePointerId);
        }

        return super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                return true;
            case 1:
            case 3:
                if (this.mActivePointerId == -1) {
                    return false;
                }

                this.mActivePointerId = -1;
                break;
            case 2:
                float y = this.getMotionEventY(event, this.mActivePointerId);
                float x = this.getMotionEventX(event, this.mActivePointerId);
                float yDiff = y - this.mLastY;
                float xDiff = x - this.mLastX;
                this.mLastY = y;
                this.mLastX = x;
                if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > (float) this.mTouchSlop) {
                    return false;
                }

                if (SwipeToLoadLayout.STATUS.isStatusDefault(this.mStatus)) {
                    if (yDiff > 0.0F && this.onCheckCanRefresh()) {
                        this.mRefreshCallback.onPrepare();
                        this.setStatus(-1);
                    } else if (yDiff < 0.0F && this.onCheckCanLoadMore()) {
                        this.mLoadMoreCallback.onPrepare();
                        this.setStatus(1);
                    }
                } else if (SwipeToLoadLayout.STATUS.isRefreshStatus(this.mStatus)) {
                    if (this.mTargetOffset <= 0) {
                        this.setStatus(0);
                        this.fixCurrentStatusLayout();
                        return false;
                    }
                } else if (SwipeToLoadLayout.STATUS.isLoadMoreStatus(this.mStatus) && this.mTargetOffset >= 0) {
                    this.setStatus(0);
                    this.fixCurrentStatusLayout();
                    return false;
                }

                if (SwipeToLoadLayout.STATUS.isRefreshStatus(this.mStatus)) {
                    if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus)) {
                        if ((float) this.mTargetOffset >= this.mRefreshTriggerOffset) {
                            this.setStatus(-2);
                        } else {
                            this.setStatus(-1);
                        }

                        this.fingerScroll(yDiff);
                    }
                } else if (SwipeToLoadLayout.STATUS.isLoadMoreStatus(this.mStatus) && (SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus) || SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus))) {
                    if ((float) (-this.mTargetOffset) >= this.mLoadMoreTriggerOffset) {
                        this.setStatus(2);
                    } else {
                        this.setStatus(1);
                    }

                    this.fingerScroll(yDiff);
                }

                return true;
            case 4:
            default:
                break;
            case 5:
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                if (pointerId != -1) {
                    this.mActivePointerId = pointerId;
                }

                this.mInitDownY = this.mLastY = this.getMotionEventY(event, this.mActivePointerId);
                this.mInitDownX = this.mLastX = this.getMotionEventX(event, this.mActivePointerId);
                break;
            case 6:
                this.onSecondaryPointerUp(event);
                this.mInitDownY = this.mLastY = this.getMotionEventY(event, this.mActivePointerId);
                this.mInitDownX = this.mLastX = this.getMotionEventX(event, this.mActivePointerId);
        }

        return super.onTouchEvent(event);
    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public boolean isRefreshEnabled() {
        return this.mRefreshEnabled;
    }

    public void setRefreshEnabled(boolean enable) {
        this.mRefreshEnabled = enable;
    }

    public boolean isLoadMoreEnabled() {
        return this.mLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean enable) {
        this.mLoadMoreEnabled = enable;
    }

    public boolean isRefreshing() {
        return SwipeToLoadLayout.STATUS.isRefreshing(this.mStatus);
    }

    public boolean isLoadingMore() {
        return SwipeToLoadLayout.STATUS.isLoadingMore(this.mStatus);
    }

    public void setRefreshHeaderView(View view) {
        if (view instanceof SwipeRefreshTrigger) {
            if (this.mHeaderView != null && this.mHeaderView != view) {
                this.removeView(this.mHeaderView);
            }

            if (this.mHeaderView != view) {
                this.mHeaderView = view;
                this.addView(view);
            }
        } else {
            Log.e(TAG, "Refresh header view must be an implement of SwipeRefreshTrigger");
        }

    }

    public void setLoadMoreFooterView(View view) {
        if (view instanceof SwipeLoadMoreTrigger) {
            if (this.mFooterView != null && this.mFooterView != view) {
                this.removeView(this.mFooterView);
            }

            if (this.mFooterView != view) {
                this.mFooterView = view;
                this.addView(this.mFooterView);
            }
        } else {
            Log.e(TAG, "Load more footer view must be an implement of SwipeLoadTrigger");
        }

    }

    public void setSwipeStyle(int style) {
        this.mStyle = style;
        this.requestLayout();
    }

    public void setDragRatio(float dragRatio) {
        this.mDragRatio = dragRatio;
    }

    public void setRefreshTriggerOffset(int offset) {
        this.mRefreshTriggerOffset = (float) offset;
    }

    public void setLoadMoreTriggerOffset(int offset) {
        this.mLoadMoreTriggerOffset = (float) offset;
    }

    public void setRefreshFinalDragOffset(int offset) {
        this.mRefreshFinalDragOffset = (float) offset;
    }

    public void setLoadMoreFinalDragOffset(int offset) {
        this.mLoadMoreFinalDragOffset = (float) offset;
    }

    public void setSwipingToRefreshToDefaultScrollingDuration(int duration) {
        this.mSwipingToRefreshToDefaultScrollingDuration = duration;
    }

    public void setReleaseToRefreshingScrollingDuration(int duration) {
        this.mReleaseToRefreshToRefreshingScrollingDuration = duration;
    }

    public void setRefreshCompleteDelayDuration(int duration) {
        this.mRefreshCompleteDelayDuration = duration;
    }

    public void setRefreshCompleteToDefaultScrollingDuration(int duration) {
        this.mRefreshCompleteToDefaultScrollingDuration = duration;
    }

    public void setDefaultToRefreshingScrollingDuration(int duration) {
        this.mDefaultToRefreshingScrollingDuration = duration;
    }

    public void setSwipingToLoadMoreToDefaultScrollingDuration(int duration) {
        this.mSwipingToLoadMoreToDefaultScrollingDuration = duration;
    }

    public void setReleaseToLoadingMoreScrollingDuration(int duration) {
        this.mReleaseToLoadMoreToLoadingMoreScrollingDuration = duration;
    }

    public void setLoadMoreCompleteDelayDuration(int duration) {
        this.mLoadMoreCompleteDelayDuration = duration;
    }

    public void setLoadMoreCompleteToDefaultScrollingDuration(int duration) {
        this.mLoadMoreCompleteToDefaultScrollingDuration = duration;
    }

    public void setDefaultToLoadingMoreScrollingDuration(int duration) {
        this.mDefaultToLoadingMoreScrollingDuration = duration;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void setRefreshing(boolean refreshing) {
        if (this.isRefreshEnabled() && this.mHeaderView != null) {
            this.mAutoLoading = refreshing;
            if (refreshing) {
                if (SwipeToLoadLayout.STATUS.isStatusDefault(this.mStatus)) {
                    this.setStatus(-1);
                    this.scrollDefaultToRefreshing();
                }
            } else if (SwipeToLoadLayout.STATUS.isRefreshing(this.mStatus)) {
                this.mRefreshCallback.onComplete();
                this.postDelayed(new Runnable() {
                    public void run() {
                        SwipeToLoadLayout.this.scrollRefreshingToDefault();
                    }
                }, (long) this.mRefreshCompleteDelayDuration);
            }

        }
    }

    public void setLoadingMore(boolean loadingMore) {
        if (this.isLoadMoreEnabled() && this.mFooterView != null) {
            this.mAutoLoading = loadingMore;
            if (loadingMore) {
                if (SwipeToLoadLayout.STATUS.isStatusDefault(this.mStatus)) {
                    this.setStatus(1);
                    this.scrollDefaultToLoadingMore();
                }
            } else if (SwipeToLoadLayout.STATUS.isLoadingMore(this.mStatus)) {
                this.mLoadMoreCallback.onComplete();
                this.postDelayed(new Runnable() {
                    public void run() {
                        SwipeToLoadLayout.this.scrollLoadingMoreToDefault();
                    }
                }, (long) this.mLoadMoreCompleteDelayDuration);
            }

        }
    }

    protected boolean canChildScrollUp() {
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, -1);
        } else if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, -1) || this.mTargetView.getScrollY() > 0;
        }
    }

    protected boolean canChildScrollDown() {
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, 1);
        } else if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            return absListView.getChildCount() > 0 && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1 || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, 1) || this.mTargetView.getScrollY() < 0;
        }
    }

    private void layoutChildren() {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int paddingLeft = this.getPaddingLeft();
        int paddingTop = this.getPaddingTop();
        int paddingRight = this.getPaddingRight();
        int paddingBottom = this.getPaddingBottom();
        if (this.mTargetView != null) {
            View footerView;
            MarginLayoutParams lp;
            int footerLeft;
            int footerBottom;
            int footerTop;
            int footerRight;
            if (this.mHeaderView != null) {
                footerView = this.mHeaderView;
                lp = (MarginLayoutParams) footerView.getLayoutParams();
                footerLeft = paddingLeft + lp.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        footerBottom = paddingTop + lp.topMargin - this.mHeaderHeight + this.mHeaderOffset;
                        break;
                    case 1:
                        footerBottom = paddingTop + lp.topMargin - this.mHeaderHeight + this.mHeaderOffset;
                        break;
                    case 2:
                        footerBottom = paddingTop + lp.topMargin;
                        break;
                    case 3:
                        footerBottom = paddingTop + lp.topMargin - this.mHeaderHeight / 2 + this.mHeaderOffset / 2;
                        break;
                    default:
                        footerBottom = paddingTop + lp.topMargin - this.mHeaderHeight + this.mHeaderOffset;
                }

                footerTop = footerLeft + footerView.getMeasuredWidth();
                footerRight = footerBottom + footerView.getMeasuredHeight();
                footerView.layout(footerLeft, footerBottom, footerTop, footerRight);
            }

            if (this.mTargetView != null) {
                footerView = this.mTargetView;
                lp = (MarginLayoutParams) footerView.getLayoutParams();
                footerLeft = paddingLeft + lp.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        footerBottom = paddingTop + lp.topMargin + this.mTargetOffset;
                        break;
                    case 1:
                        footerBottom = paddingTop + lp.topMargin;
                        break;
                    case 2:
                        footerBottom = paddingTop + lp.topMargin + this.mTargetOffset;
                        break;
                    case 3:
                        footerBottom = paddingTop + lp.topMargin + this.mTargetOffset;
                        break;
                    default:
                        footerBottom = paddingTop + lp.topMargin + this.mTargetOffset;
                }

                footerTop = footerLeft + footerView.getMeasuredWidth();
                footerRight = footerBottom + footerView.getMeasuredHeight();
                footerView.layout(footerLeft, footerBottom, footerTop, footerRight);
            }

            if (this.mFooterView != null) {
                footerView = this.mFooterView;
                lp = (MarginLayoutParams) footerView.getLayoutParams();
                footerLeft = paddingLeft + lp.leftMargin;
                switch (this.mStyle) {
                    case 0:
                        footerBottom = height - paddingBottom - lp.bottomMargin + this.mFooterHeight + this.mFooterOffset;
                        break;
                    case 1:
                        footerBottom = height - paddingBottom - lp.bottomMargin + this.mFooterHeight + this.mFooterOffset;
                        break;
                    case 2:
                        footerBottom = height - paddingBottom - lp.bottomMargin;
                        break;
                    case 3:
                        footerBottom = height - paddingBottom - lp.bottomMargin + this.mFooterHeight / 2 + this.mFooterOffset / 2;
                        break;
                    default:
                        footerBottom = height - paddingBottom - lp.bottomMargin + this.mFooterHeight + this.mFooterOffset;
                }

                footerTop = footerBottom - footerView.getMeasuredHeight();
                footerRight = footerLeft + footerView.getMeasuredWidth();
                footerView.layout(footerLeft, footerTop, footerRight, footerBottom);
            }

            if (this.mStyle != 0 && this.mStyle != 1) {
                if ((this.mStyle == 2 || this.mStyle == 3) && this.mTargetView != null) {
                    this.mTargetView.bringToFront();
                }
            } else {
                if (this.mHeaderView != null) {
                    this.mHeaderView.bringToFront();
                }

                if (this.mFooterView != null) {
                    this.mFooterView.bringToFront();
                }
            }

        }
    }

    private void fixCurrentStatusLayout() {
        if (SwipeToLoadLayout.STATUS.isRefreshing(this.mStatus)) {
            this.mTargetOffset = (int) (this.mRefreshTriggerOffset + 0.5F);
            this.mHeaderOffset = this.mTargetOffset;
            this.mFooterOffset = 0;
            this.layoutChildren();
            this.invalidate();
        } else if (SwipeToLoadLayout.STATUS.isStatusDefault(this.mStatus)) {
            this.mTargetOffset = 0;
            this.mHeaderOffset = 0;
            this.mFooterOffset = 0;
            this.layoutChildren();
            this.invalidate();
        } else if (SwipeToLoadLayout.STATUS.isLoadingMore(this.mStatus)) {
            this.mTargetOffset = -((int) (this.mLoadMoreTriggerOffset + 0.5F));
            this.mHeaderOffset = 0;
            this.mFooterOffset = this.mTargetOffset;
            this.layoutChildren();
            this.invalidate();
        }

    }

    private void fingerScroll(float yDiff) {
        float ratio = this.mDragRatio;
        float yScrolled = yDiff * ratio;
        float tmpTargetOffset = yScrolled + (float) this.mTargetOffset;
        if (tmpTargetOffset > 0.0F && this.mTargetOffset < 0 || tmpTargetOffset < 0.0F && this.mTargetOffset > 0) {
            yScrolled = (float) (-this.mTargetOffset);
        }

        if (this.mRefreshFinalDragOffset >= this.mRefreshTriggerOffset && tmpTargetOffset > this.mRefreshFinalDragOffset) {
            yScrolled = this.mRefreshFinalDragOffset - (float) this.mTargetOffset;
        } else if (this.mLoadMoreFinalDragOffset >= this.mLoadMoreTriggerOffset && -tmpTargetOffset > this.mLoadMoreFinalDragOffset) {
            yScrolled = -this.mLoadMoreFinalDragOffset - (float) this.mTargetOffset;
        }

        if (SwipeToLoadLayout.STATUS.isRefreshStatus(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, false);
        } else if (SwipeToLoadLayout.STATUS.isLoadMoreStatus(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, false);
        }

        this.updateScroll(yScrolled);
    }

    private void autoScroll(float yScrolled) {
        if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, true);
        } else if (SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, false, true);
        } else if (SwipeToLoadLayout.STATUS.isRefreshing(this.mStatus)) {
            this.mRefreshCallback.onMove(this.mTargetOffset, true, true);
        } else if (SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, true);
        } else if (SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, false, true);
        } else if (SwipeToLoadLayout.STATUS.isLoadingMore(this.mStatus)) {
            this.mLoadMoreCallback.onMove(this.mTargetOffset, true, true);
        }

        this.updateScroll(yScrolled);
    }

    private void updateScroll(float yScrolled) {
        if (yScrolled != 0.0F) {
            this.mTargetOffset = (int) ((float) this.mTargetOffset + yScrolled);
            if (SwipeToLoadLayout.STATUS.isRefreshStatus(this.mStatus)) {
                this.mHeaderOffset = this.mTargetOffset;
                this.mFooterOffset = 0;
            } else if (SwipeToLoadLayout.STATUS.isLoadMoreStatus(this.mStatus)) {
                this.mFooterOffset = this.mTargetOffset;
                this.mHeaderOffset = 0;
            }

            if (this.mDebug) {
                Log.i(TAG, "mTargetOffset = " + this.mTargetOffset);
            }

            this.layoutChildren();
            this.invalidate();
        }
    }

    private void onActivePointerUp() {
        if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus)) {
            this.scrollSwipingToRefreshToDefault();
        } else if (SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus)) {
            this.scrollSwipingToLoadMoreToDefault();
        } else if (SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus)) {
            this.mRefreshCallback.onRelease();
            this.scrollReleaseToRefreshToRefreshing();
        } else if (SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus)) {
            this.mLoadMoreCallback.onRelease();
            this.scrollReleaseToLoadMoreToLoadingMore();
        }

    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }

    }

    private void scrollDefaultToRefreshing() {
        this.mAutoScroller.autoScroll((int) (this.mRefreshTriggerOffset + 0.5F), this.mDefaultToRefreshingScrollingDuration);
    }

    private void scrollDefaultToLoadingMore() {
        this.mAutoScroller.autoScroll(-((int) (this.mLoadMoreTriggerOffset + 0.5F)), this.mDefaultToLoadingMoreScrollingDuration);
    }

    private void scrollSwipingToRefreshToDefault() {
        this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mSwipingToRefreshToDefaultScrollingDuration);
    }

    private void scrollSwipingToLoadMoreToDefault() {
        this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mSwipingToLoadMoreToDefaultScrollingDuration);
    }

    private void scrollReleaseToRefreshToRefreshing() {
        this.mAutoScroller.autoScroll(this.mHeaderHeight - this.mHeaderOffset, this.mReleaseToRefreshToRefreshingScrollingDuration);
    }

    private void scrollReleaseToLoadMoreToLoadingMore() {
        this.mAutoScroller.autoScroll(-this.mFooterOffset - this.mFooterHeight, this.mReleaseToLoadMoreToLoadingMoreScrollingDuration);
    }

    private void scrollRefreshingToDefault() {
        this.mAutoScroller.autoScroll(-this.mHeaderOffset, this.mRefreshCompleteToDefaultScrollingDuration);
    }

    private void scrollLoadingMoreToDefault() {
        this.mAutoScroller.autoScroll(-this.mFooterOffset, this.mLoadMoreCompleteToDefaultScrollingDuration);
    }

    private void autoScrollFinished() {
        int mLastStatus = this.mStatus;
        if (SwipeToLoadLayout.STATUS.isReleaseToRefresh(this.mStatus)) {
            this.setStatus(-3);
            this.fixCurrentStatusLayout();
            this.mRefreshCallback.onRefresh();
        } else if (SwipeToLoadLayout.STATUS.isRefreshing(this.mStatus)) {
            this.setStatus(0);
            this.fixCurrentStatusLayout();
            this.mRefreshCallback.onReset();
        } else if (SwipeToLoadLayout.STATUS.isSwipingToRefresh(this.mStatus)) {
            if (this.mAutoLoading) {
                this.mAutoLoading = false;
                this.setStatus(-3);
                this.fixCurrentStatusLayout();
                this.mRefreshCallback.onRefresh();
            } else {
                this.setStatus(0);
                this.fixCurrentStatusLayout();
                this.mRefreshCallback.onReset();
            }
        } else if (!SwipeToLoadLayout.STATUS.isStatusDefault(this.mStatus)) {
            if (SwipeToLoadLayout.STATUS.isSwipingToLoadMore(this.mStatus)) {
                if (this.mAutoLoading) {
                    this.mAutoLoading = false;
                    this.setStatus(3);
                    this.fixCurrentStatusLayout();
                    this.mLoadMoreCallback.onLoadMore();
                } else {
                    this.setStatus(0);
                    this.fixCurrentStatusLayout();
                    this.mLoadMoreCallback.onReset();
                }
            } else if (SwipeToLoadLayout.STATUS.isLoadingMore(this.mStatus)) {
                this.setStatus(0);
                this.fixCurrentStatusLayout();
                this.mLoadMoreCallback.onReset();
            } else {
                if (!SwipeToLoadLayout.STATUS.isReleaseToLoadMore(this.mStatus)) {
                    throw new IllegalStateException("illegal state: " + SwipeToLoadLayout.STATUS.getStatus(this.mStatus));
                }

                this.setStatus(3);
                this.fixCurrentStatusLayout();
                this.mLoadMoreCallback.onLoadMore();
            }
        }

        if (this.mDebug) {
            Log.i(TAG, SwipeToLoadLayout.STATUS.getStatus(mLastStatus) + " -> " + SwipeToLoadLayout.STATUS.getStatus(this.mStatus));
        }

    }

    private boolean onCheckCanRefresh() {
        return this.mRefreshEnabled && !this.canChildScrollUp() && this.mHasHeaderView && this.mRefreshTriggerOffset > 0.0F;
    }

    private boolean onCheckCanLoadMore() {
        return this.mLoadMoreEnabled && !this.canChildScrollDown() && this.mHasFooterView && this.mLoadMoreTriggerOffset > 0.0F;
    }

    private float getMotionEventY(MotionEvent event, int activePointerId) {
        int index = MotionEventCompat.findPointerIndex(event, activePointerId);
        return index < 0 ? -1.0F : MotionEventCompat.getY(event, index);
    }

    private float getMotionEventX(MotionEvent event, int activePointId) {
        int index = MotionEventCompat.findPointerIndex(event, activePointId);
        return index < 0 ? -1.0F : MotionEventCompat.getX(event, index);
    }

    private void setStatus(int status) {
        this.mStatus = status;
        if (this.mDebug) {
            SwipeToLoadLayout.STATUS.printStatus(status);
        }

    }

    private static final class STATUS {
        private static final int STATUS_REFRESH_RETURNING = -4;
        private static final int STATUS_REFRESHING = -3;
        private static final int STATUS_RELEASE_TO_REFRESH = -2;
        private static final int STATUS_SWIPING_TO_REFRESH = -1;
        private static final int STATUS_DEFAULT = 0;
        private static final int STATUS_SWIPING_TO_LOAD_MORE = 1;
        private static final int STATUS_RELEASE_TO_LOAD_MORE = 2;
        private static final int STATUS_LOADING_MORE = 3;
        private static final int STATUS_LOAD_MORE_RETURNING = 4;

        private STATUS() {
        }

        private static boolean isRefreshing(int status) {
            return status == -3;
        }

        private static boolean isLoadingMore(int status) {
            return status == 3;
        }

        private static boolean isReleaseToRefresh(int status) {
            return status == -2;
        }

        private static boolean isReleaseToLoadMore(int status) {
            return status == 2;
        }

        private static boolean isSwipingToRefresh(int status) {
            return status == -1;
        }

        private static boolean isSwipingToLoadMore(int status) {
            return status == 1;
        }

        private static boolean isRefreshStatus(int status) {
            return status < 0;
        }

        public static boolean isLoadMoreStatus(int status) {
            return status > 0;
        }

        private static boolean isStatusDefault(int status) {
            return status == 0;
        }

        private static String getStatus(int status) {
            String statusInfo;
            switch (status) {
                case -4:
                    statusInfo = "status_refresh_returning";
                    break;
                case -3:
                    statusInfo = "status_refreshing";
                    break;
                case -2:
                    statusInfo = "status_release_to_refresh";
                    break;
                case -1:
                    statusInfo = "status_swiping_to_refresh";
                    break;
                case 0:
                    statusInfo = "status_default";
                    break;
                case 1:
                    statusInfo = "status_swiping_to_load_more";
                    break;
                case 2:
                    statusInfo = "status_release_to_load_more";
                    break;
                case 3:
                    statusInfo = "status_loading_more";
                    break;
                case 4:
                    statusInfo = "status_load_more_returning";
                    break;
                default:
                    statusInfo = "status_illegal!";
            }

            return statusInfo;
        }

        private static void printStatus(int status) {
            Log.i(SwipeToLoadLayout.TAG, "printStatus:" + getStatus(status));
        }
    }

    private class AutoScroller implements Runnable {
        private Scroller mScroller = new Scroller(SwipeToLoadLayout.this.getContext());
        private int mmLastY;
        private boolean mRunning = false;
        private boolean mAbort = false;

        public AutoScroller() {
        }

        public void run() {
            boolean finish = !this.mScroller.computeScrollOffset() || this.mScroller.isFinished();
            int currY = this.mScroller.getCurrY();
            int yDiff = currY - this.mmLastY;
            if (finish) {
                this.finish();
            } else {
                this.mmLastY = currY;
                SwipeToLoadLayout.this.autoScroll((float) yDiff);
                SwipeToLoadLayout.this.post(this);
            }

        }

        private void finish() {
            this.mmLastY = 0;
            this.mRunning = false;
            SwipeToLoadLayout.this.removeCallbacks(this);
            if (!this.mAbort) {
                SwipeToLoadLayout.this.autoScrollFinished();
            }

        }

        public void abortIfRunning() {
            if (this.mRunning) {
                if (!this.mScroller.isFinished()) {
                    this.mAbort = true;
                    this.mScroller.forceFinished(true);
                }

                this.finish();
                this.mAbort = false;
            }

        }

        private void autoScroll(int yScrolled, int duration) {
            SwipeToLoadLayout.this.removeCallbacks(this);
            this.mmLastY = 0;
            if (!this.mScroller.isFinished()) {
                this.mScroller.forceFinished(true);
            }

            this.mScroller.startScroll(0, 0, 0, yScrolled, duration);
            SwipeToLoadLayout.this.post(this);
            this.mRunning = true;
        }
    }

    abstract class LoadMoreCallback implements SwipeTrigger, SwipeLoadMoreTrigger {
        LoadMoreCallback() {
        }
    }

    abstract class RefreshCallback implements SwipeTrigger, SwipeRefreshTrigger {
        RefreshCallback() {
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public static final class STYLE {
        public static final int CLASSIC = 0;
        public static final int ABOVE = 1;
        public static final int BLEW = 2;
        public static final int SCALE = 3;

        public STYLE() {
        }
    }
}

