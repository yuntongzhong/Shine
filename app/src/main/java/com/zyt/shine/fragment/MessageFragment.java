package com.zyt.shine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyt.shine.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  用于展示消息的Fragment
 */
public class MessageFragment extends Fragment {
	Context context;
	private static  MessageFragment messageFragment;

	public static MessageFragment getInstance(){
		if(messageFragment==null){
			messageFragment=new MessageFragment();
		}
		return messageFragment;
	}


	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private List<String> titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	private MyAdapter myAdapter;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
			myAdapter.setListViews(imageViews);// 重构adapter对象  这是一个很重要
		};
	};
	LinearLayout dotlayout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.message_layout,
				container, false);

		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		titles=new ArrayList<String>();
		titles.add("巩俐不低俗，我就不能低俗");
		titles.add("扑树又回来啦！再唱经典老歌引万人大合唱");
		titles.add("揭秘北京电影如何升级");
		titles.add("乐视网TV版大派送");
		titles.add("热血屌丝的反杀");

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(messageLayout.findViewById(R.id.v_dot0));
		dots.add(messageLayout.findViewById(R.id.v_dot1));
		dots.add(messageLayout.findViewById(R.id.v_dot2));
		dots.add(messageLayout.findViewById(R.id.v_dot3));
		dots.add(messageLayout.findViewById(R.id.v_dot4));

		tv_title = (TextView) messageLayout.findViewById(R.id.tv_title);
		tv_title.setText(titles.get(0));//

		viewPager = (ViewPager) messageLayout.findViewById(R.id.vp);
		myAdapter=new MyAdapter(imageViews);
		viewPager.setAdapter(myAdapter);// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.addOnPageChangeListener(new MyPageChangeListener());
		dotlayout = (LinearLayout) messageLayout.findViewById(R.id.add_dotll);
//		ImageButton button = (ImageButton) messageLayout.findViewById(R.id.img_add);
//		button.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				addItem();
//				System.out.println("back onclick");
//			}
//		});
		return messageLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context=context;
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 *
	 * @author Administrator
	 *
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 *
	 * @author Administrator
	 *
	 */
	private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles.get(position));
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;

		}

		/**
		 * 此方法是在状态改变的时候调用，其中arg0这个参数有三种状态 （0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时
		 * 辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
		 */
		public void onPageScrollStateChanged(int arg0) {

		}

		/**
		 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。其中三个参数的含义分别为：
		 *
		 * arg0 :当前页面，及你点击滑动的页面
		 *
		 * arg1:当前页面偏移的百分比
		 *
		 * arg2:当前页面偏移的像素位置
		 */
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 *
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends PagerAdapter {
		private int size;// 页数
		List<ImageView> imageViews;
		public MyAdapter(List<ImageView> imageViews){
			this.imageViews=imageViews;
			size = imageViews == null ? 0 : imageViews.size();

		}


		public void setListViews(List<ImageView> listViews) {// 自己写的一个方法用来添加数据  这个可是重点啊
			this.imageViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}


		@Override
		public int getCount() {
			return size;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1 % imageViews.size()));
			return imageViews.get(arg1 % imageViews.size());
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(imageViews.get(arg1 % size));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

	}

	private void addItem() {
		//取得默认原点的height
		int height = dots.get(0).getHeight();
		//初始化一个imageview
		ImageView imageView=new ImageView(context);
		imageView.setBackgroundResource(R.drawable.ic_launcher);
		imageViews.add(imageView);
		//初始化一个原点imageview
		ImageView dotimg = new ImageView(context);
		//设置原点的高和宽
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(height,
				height);
		//设置外边距
		lp.setMargins(2, 0, 2, 0);
		dotimg.setLayoutParams(lp);
		//设置背景
		dotimg.setBackgroundResource(R.drawable.dot_normal);
		dots.add(dotimg);
		//添加原点到父容器
		dotlayout.addView(dotimg);
		//添加一条字符串数据
		titles.add("新增的String");
		myAdapter.setListViews(imageViews);// 重构adapter对象  这是一个很重要
		myAdapter.notifyDataSetChanged();
	}


}
