package com.zyt.shine.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zyt.shine.R;
import com.zyt.shine.ui.view.AutoPlayViewPage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示动态的Fragment
 */
public class NewsFragment extends Fragment {

    private final long REFRESH_DELAY = 3000;
    private AutoPlayViewPage mImageCycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter arrayAdapter;
    private ListView listView;
    private List<String> listString = new ArrayList<String>();
    private  List<AutoPlayViewPage.ImageInfo> listImg = new ArrayList<AutoPlayViewPage.ImageInfo>();
    private ResfreshHandler resfreshHandler;

    public static class ResfreshHandler extends Handler {
        WeakReference<NewsFragment> mFragmentWeakReference;

        ResfreshHandler(NewsFragment newsFragment) {
            mFragmentWeakReference = new WeakReference<NewsFragment>(newsFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            NewsFragment theFragment = mFragmentWeakReference.get();
            switch (msg.what) {
                case 1:
                    theFragment.resfreshData(msg);
                    break;
            }
        }
    }
    public void resfreshData(Message msg) {
        listString.add(msg.obj.toString());
        swipeRefreshLayout.setRefreshing(false);
        arrayAdapter.notifyDataSetChanged();
        //swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup newsLayout = (ViewGroup) inflater.inflate(R.layout.news_fragment, container,
                false);
        listView = (ListView) newsLayout.findViewById(R.id.list_view);
        ViewGroup headerGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.header_list_layout, null, false);
        listView.addHeaderView(headerGroup);
        mImageCycleView = (AutoPlayViewPage) headerGroup.findViewById(R.id.news_auto_play);
        mImageCycleView.loadData(listImg);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listString);
        listView.setAdapter(arrayAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) newsLayout.findViewById(R.id.news_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.mediumturquoise,
                R.color.colorAccent,
                R.color.slateblue);
        afterViews();
        return newsLayout;
    }

    protected void afterViews() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(REFRESH_DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = resfreshHandler.obtainMessage();
                        message.what = 1;
                        message.obj = "新增数据" + listString.size();
                        resfreshHandler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    void initdata() {
        resfreshHandler=new ResfreshHandler(this);
        //res图片资源
        listImg.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        listImg.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
        listImg.add(new AutoPlayViewPage.ImageInfo(R.mipmap.d, "乐视网TV版大派送", ""));
        listImg.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
        for (int i = 0; i < 5; i++) {
            this.listString.add("数据");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
    }
}