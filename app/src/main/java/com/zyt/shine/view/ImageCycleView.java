package com.zyt.shine.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyt.shine.R;
import com.zyt.shine.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyt on 2016/2/29.
 */
public class ImageCycleView extends FrameLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ImageCycleViewPager mViewPager;
    /**
     * 数据集合
     * Map<String,String> map=new HashMap<String, String>();
     * map.put("","");
     */
    private List<ImageInfo> data = new ArrayList<ImageInfo>();
    /**
     * 加载图片回调函数
     */
    private LoadImageCallBack mLoadImageCallBack;

    /**
     * 图片轮播指示器容器
     */
    private LinearLayout mIndicationGroup;
    /**
     * 轮播的总数
     */
    private int mCount = 0;
    /**
     * 指示器的尺寸（单位：dp）
     */
    private float indicationSize = 6.0f;
    /**
     * 指示器的左右边距（单位：dp）
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


    public ImageCycleView(Context context) {
        super(context);
        init(context);
    }

    public ImageCycleView(Context context, AttributeSet attrs) {
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
     *
     * @author 代凯男
     */
    private void initView() {
        View.inflate(mContext, R.layout.auto_play_viewpage_view, this);
        FrameLayout fl_image_cycle = (FrameLayout) findViewById(R.id.fl_image_cycle);
        mViewPager = new ImageCycleViewPager(mContext);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl_image_cycle.addView(mViewPager);
        mViewPager.addOnPageChangeListener(new ImageCyclePageChangeListener());
        mIndicationGroup = (LinearLayout) findViewById(R.id.ll_indication_group);
        mText = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 图片轮播是自动滚动状态  true 自动滚动，false 图片不能自动滚动只能手动左右滑动
     */
    private boolean isAutoCycle = true;
    /**
     * 自动轮播时间间隔默认5秒
     */
    private long mCycleDelayed = 3000;

    /**
     * 设置是否自动无限轮播
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
     * @param list     数据
     * @param callBack 如何加载图片及显示的回调方法 not null
     */
    public void loadData(List<ImageInfo> list, LoadImageCallBack callBack) {
        data = list;
        mCount = list.size();
        initIndication();
        if (callBack == null) {
            new IllegalArgumentException("LoadImageCallBack 回调函数不能为空！");
        }
        mLoadImageCallBack = callBack;
        mViewPager.setAdapter(new ImageCycleAdapter());
        //最大值中间 的第一个
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % mCount));

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
                imageView.setBackgroundResource(R.drawable.dot_focused);
            } else {
                imageView.setBackgroundResource(R.drawable.dot_normal);
            }
            mIndicationGroup.addView(imageView);
        }
    }

    public static class ImageInfo {
        public ImageInfo(Object image, String text, Object value) {
            this.image = image;
            this.text = text;
            this.value = value;
        }

        public Object image;
        public String text = "";
        public Object value;
    }


    /**
     * 加载图片并显示回调接口
     */
    public interface LoadImageCallBack {
        /**
         * 自己如何设置加载图片
         *
         * @param imageInfo 数据信息
         */
        ImageView loadAndDisplay(ImageInfo imageInfo);
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
            String text = data.get(index).text;
            mText.setText(TextUtils.isEmpty(text) ? "" : text);
            //恢复默认没有获得焦点指示器样式
            ((mIndicationGroup.getChildAt(preIndex))).setBackgroundResource(R.drawable.dot_normal);
            // 设置当前显示图片的指示器样式
            ((mIndicationGroup.getChildAt(index))).setBackgroundResource(R.drawable.dot_focused);
            preIndex = index;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }
    }

    /**
     * 图片轮播适配器
     */
    private class ImageCycleAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageInfo imageInfo = data.get(position % mCount);
            ImageView imageView = mLoadImageCallBack.loadAndDisplay(imageInfo);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
    }


    /**
     * 开始图片轮播
     */
    private void startImageCycle() {
        handler.sendEmptyMessageDelayed(0, mCycleDelayed);
    }

    /**
     * 暂停图片轮播
     */
    private void stopImageCycle() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 实现自动轮播
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                handler.sendEmptyMessageDelayed(0, mCycleDelayed);
            }
            return false;
        }
    });

    /**
     * 触摸停止计时器，抬起启动计时器
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
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
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 停止图片滚动
        stopImageCycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoCycle) {
            startImageCycle();
        }
    }

    /**
     * 自定义ViewPager主要用于事件处理
     */
    public class ImageCycleViewPager extends ViewPager {

        public ImageCycleViewPager(Context context) {
            super(context);
        }

        public ImageCycleViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * 事件拦截
         */
        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev);
        }

        /**
         * 事件分发
         */
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return super.dispatchTouchEvent(ev);
        }

        /**
         * 事件处理
         */
        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return super.onTouchEvent(ev);
        }
    }
}
