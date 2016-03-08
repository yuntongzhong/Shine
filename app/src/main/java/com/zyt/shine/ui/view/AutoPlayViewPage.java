package com.zyt.shine.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyt.shine.R;
import com.zyt.shine.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyt on 2016/2/29.
 * <p/>
 * <p>无限自动轮播控件AutoPlayViewPage</p>
 * <p>显示的页数应小于等于最大缓存页数的2倍，否则在刷新时会引发ANR异常</p>
 * <p>设置viewpage的最大缓存页数的方法为 mViewPager.setOffscreenPageLimit(5)</p>
 * <p>与swipeRefreshLayout同时同时使用时，使用setSwipeRefreshLayoutTouch()解决滑动冲突</p>
 */
public class AutoPlayViewPage extends FrameLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mViewPager;
    /**
     * 数据源
     */
    private List<ImageInfo> data = new ArrayList<ImageInfo>();
    /**
     * 加载图片回调函数
     */
    private final int mCycleDelayedMsg = 0;

    /**
     * 图片轮播指示器容器
     */
    private LinearLayout mIndicationGroup;
    /**
     * 轮播的总数
     */
    private int mCount = 0;
    /**
     * 指示器的尺寸（单位：PX）
     * 后面会将px转成dp
     */
    private float indicationSize = 6.0f;
    /**
     * 指示器的左右边距（单位：PX）
     */
    private float indicationMargin = 1.5f;
    /**
     * 单击事件监听器
     */
    private OnPageClickListener mOnPageClickListener;
    /**
     * 图片文本提示
     */
    private TextView mText;

    /**
     * 图片轮播是自动滚动状态  true 自动滚动，false 图片不能自动滚动只能手动左右滑动
     */
    private boolean isAutoCycle = true;
    /**
     * 自动轮播时间间隔默认3秒
     */
    private long mCycleDelayed = 3000;

    private ImageCycleAdapter imageCycleAdapter;

    public AutoPlayViewPage(Context context) {
        super(context);
        init(context);
    }

    public AutoPlayViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化基础信息
     *
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        initView();
    }

    /**
     * 初始化view控件
     */
    private void initView() {
        View.inflate(mContext, R.layout.auto_play_viewpage_view, this);
        FrameLayout fl_image_cycle = (FrameLayout) findViewById(R.id.fl_image_cycle);
        mViewPager = new ViewPager(mContext);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl_image_cycle.addView(mViewPager);
        mViewPager.addOnPageChangeListener(new ImageCyclePageChangeListener());
        mIndicationGroup = (LinearLayout) findViewById(R.id.ll_indication_group);
        mText = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 实现自动轮播
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mViewPager != null && msg.what == mCycleDelayedMsg) {
                mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1));
                handler.sendEmptyMessageDelayed(mCycleDelayedMsg, mCycleDelayed);
            }
            //数据刷新时what为1
            else if (mViewPager != null && msg.what == 1) {
                mViewPager.setCurrentItem(getNearestIndication());
                initIndication();
                handler.sendEmptyMessageDelayed(mCycleDelayedMsg, mCycleDelayed);
            }
            return false;
        }
    });

    /**
     * 获取与当前位置最近的位于第一位的点
     *
     * @return
     */
    private int getNearestIndication() {
        int current = mViewPager.getCurrentItem();//获取viewpage的当齐前位置
        int currentIndex = current % mCount;//当前实际位置
        int beforeIndex = currentIndex;//前面第一个的距离
        int afterIndex = mCount - currentIndex;//后面第一个的距离
        if (currentIndex == 0) {
            return current;
        }
        if (beforeIndex <= afterIndex) {
            current = current - beforeIndex;
        } else {
            current = current + afterIndex;
        }
        return current;
    }

    /**
     * 设置自动轮播时间间隔
     *
     * @param delayed 自动轮播时间间隔
     */
    public void setCycleDelayed(long delayed) {
        mCycleDelayed = delayed;
    }

    /**
     * 设置是否自动无限轮播
     *
     * @param state
     */
    public void setAutoCycle(Boolean state) {
        isAutoCycle = state;
    }

    /**
     * 加载显示的数据  网络图片资源及标题
     *
     * @param list 数据
     */
    public void loadData(List<ImageInfo> list) {
        data.clear();
        data.addAll(list);
        mCount = list.size();
        if (list.size() == 0) {
            data.add(new ImageInfo("", "", ""));
            mCount = 1;
        }
        initIndication();
        //设置最大缓存页数，显示的页数应小于等于此数的2倍，否则在刷新时会引发ANR异常
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(imageCycleAdapter = new ImageCycleAdapter());
        //最大值中间 的第一个
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % mCount));
    }

    public void notifyDataChanged(List<ImageInfo> list) {
        stopImageCycle();
        if (list.size() == 0 || list.isEmpty()) {
            return;
        }
        data.clear();
        data.addAll(list);
        mCount = data.size();
        imageCycleAdapter.notifyDataSetChanged();
        handler.sendEmptyMessage(1);
    }

    /**
     * 设置点击事件监听回调函数
     *
     * @param listener
     */
    public void setOnPageClickListener(OnPageClickListener listener) {
        mOnPageClickListener = listener;
    }

    /**
     * 设置当前位置
     *
     * @param item
     */
    public void setCurrentItem(int item) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(item);
        }
    }

    /**
     * 保存view的状态
     *
     * @return
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.mCuurrentIndex = mViewPager.getCurrentItem();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentItem(ss.mCuurrentIndex);
    }

    /**
     * 初始化指标器
     */
    private void initIndication() {
        mIndicationGroup.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView imageView = new ImageView(mContext);
            int size = DisplayUtil.dip2px(mContext, indicationSize);
            int margin = DisplayUtil.dip2px(mContext, indicationMargin);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(margin, 0, margin, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.dot_focused_shape);
            } else {
                imageView.setBackgroundResource(R.drawable.dot_normal_shape);
            }
            mIndicationGroup.addView(imageView);
        }
    }

    /**
     * 轮播控件的监听事件
     */
    public interface OnPageClickListener {
        /**
         * 单击图片事件
         *
         * @param imageView 被点击的View对象
         * @param imageInfo 数据信息
         */
        void onClick(View imageView, ImageInfo imageInfo);
    }

    /**
     * 图片信息类
     */
    public static class ImageInfo implements Parcelable {
        public Object image;
        public String title = "";
        public Object value;

        public ImageInfo(Object image, String text, Object value) {
            this.image = image;
            this.title = text;
            this.value = value;
        }

        protected ImageInfo(Parcel in) {
            title = in.readString();
        }

        public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
            @Override
            public ImageInfo createFromParcel(Parcel in) {
                return new ImageInfo(in);
            }

            @Override
            public ImageInfo[] newArray(int size) {
                return new ImageInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
        }
    }

    /**
     * 轮播图片监听
     */
    private final class ImageCyclePageChangeListener implements OnPageChangeListener {

        //上次指示器指示的位置,开始为默认位置0
        private int preIndex = 0;

        @Override
        public void onPageSelected(int index) {
            index = index % mCount;
            //更新文本信息
            String text = data.get(index).title;
            mText.setText(TextUtils.isEmpty(text) ? "" : text);
            //恢复默认没有获得焦点指示器样式
            ((mIndicationGroup.getChildAt(preIndex))).setBackgroundResource(R.drawable.dot_normal_shape);
            // 设置当前显示图片的指示器样式
            ((mIndicationGroup.getChildAt(index))).setBackgroundResource(R.drawable.dot_focused_shape);
            preIndex = index;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 开始图片轮播
     */
    private void startImageCycle() {
        handler.sendEmptyMessageDelayed(mCycleDelayedMsg, mCycleDelayed);
    }

    /**
     * 暂停图片轮播
     */
    private void stopImageCycle() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 当使用SwipeRefreshLayout作为外层布局时，使用此方法来解决滑动冲突
     *
     * @param swipeRefreshLayout
     */
    public void setSwipeRefreshLayoutTouch(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            return;
        }
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 触摸停止计时器，抬起启动计时器
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (isAutoCycle) {
                // 开始图片滚动
                startImageCycle();
            }
        } else {
            if (isAutoCycle) {
                // 停止图片滚动
                stopImageCycle();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * view销毁时调用此方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止图片滚动
        stopImageCycle();
    }

    /**
     * onAttachedToWindow是在第一次onDraw前调用的
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoCycle) {
            startImageCycle();
        }
    }

    /**
     * 图片轮播适配器
     */
    private class ImageCycleAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageInfo imageInfo = data.get(position % mCount);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(mContext.getApplicationContext()).load(imageInfo.image)
                    .centerCrop()
                    .into(imageView);
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnPageClickListener != null) {
                        mOnPageClickListener.onClick(v, imageInfo);
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private int mChildCount = 0;


        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();

        }

        /**
         * 覆盖getItemPosition()方法，当调用notifyDataSetChanged时，
         * 让getItemPosition方法人为的返回POSITION_NONE，
         * 从而达到强迫viewpager重绘所有item的目的。
         *
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }

    /**
     * 用于保存view的状态
     */
    static class SavedState extends BaseSavedState {
        int mCuurrentIndex = 0;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mCuurrentIndex = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mCuurrentIndex);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}