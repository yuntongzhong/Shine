package com.zyt.shine.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zyt.shine.R;
import com.zyt.shine.entity.NewsEntity;
import com.zyt.shine.ui.view.AutoPlayViewPage;
import com.zyt.shine.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用于展示消息的Fragment
 */
public class MessageFragment extends Fragment {
    private AutoPlayViewPage mImageCycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<AutoPlayViewPage.ImageInfo> list;
    List<AutoPlayViewPage.ImageInfo> listTest=new ArrayList<AutoPlayViewPage.ImageInfo>();
    Random random = new Random();
    boolean isTest=true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if(isTest){
                       mImageCycleView.notifyDataChanged(listTest);
                    }else {
                     mImageCycleView.notifyDataChanged(list);
                    }
                    isTest=!isTest;
                    swipeRefreshLayout.setRefreshing(false);
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup messageLayout = (ViewGroup) inflater.inflate(R.layout.message_fragment,
                container, false);
        mImageCycleView = (AutoPlayViewPage) messageLayout.findViewById(R.id.icv_topView);
        mImageCycleView.loadData(list);
        swipeRefreshLayout = (SwipeRefreshLayout) messageLayout.findViewById(R.id.msg_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.mediumturquoise,
                R.color.colorAccent,
                R.color.slateblue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        mImageCycleView.setOnTouchListener(new View.OnTouchListener() {
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
        return messageLayout;
    }

    private void initdata() {
        list = new ArrayList<AutoPlayViewPage.ImageInfo>();
        //res图片资源
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.d, "乐视网TV版大派送", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.d, "乐视网TV版大派送", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));

        listTest.add(new AutoPlayViewPage.ImageInfo("http://img2.imgtn.bdimg.com/it/u=3922277475,817684749&fm=21&gp=0.jpg", "网络图片1", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg", "网络图片2", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg", "网络图片3", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic1.ooopic.com/uploadfilepic/sheji/2009-05-05/OOOPIC_vip4_20090505079ae095187332ea.jpg", "网络图片4", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://img2.imgtn.bdimg.com/it/u=3922277475,817684749&fm=21&gp=0.jpg", "网络图片1", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg", "网络图片2", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg", "网络图片3", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic1.ooopic.com/uploadfilepic/sheji/2009-05-05/OOOPIC_vip4_20090505079ae095187332ea.jpg", "网络图片4", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://img2.imgtn.bdimg.com/it/u=3922277475,817684749&fm=21&gp=0.jpg", "网络图片1", ""));
        listTest.add(new AutoPlayViewPage.ImageInfo("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg", "网络图片2", ""));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
    }
}
