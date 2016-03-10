package com.zyt.shine.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.zyt.shine.R;
import com.zyt.shine.entity.NewsEntity;
import com.zyt.shine.ui.adapter.Bookends;
import com.zyt.shine.ui.adapter.NewsRecyAdapter;
import com.zyt.shine.ui.view.AutoPlayViewPage;
import com.zyt.shine.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示联系人的Fragment
 */
public class ContactsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NewsEntity> list = new ArrayList<NewsEntity>();;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsRecyAdapter newsRecyAdapter;
    private AutoPlayViewPage mImageCycleView;

    private Bookends<NewsRecyAdapter> mBookends;

    List<AutoPlayViewPage.ImageInfo> listTest=new ArrayList<AutoPlayViewPage.ImageInfo>();
    ViewGroup headerGroup;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    list.add(0, new NewsEntity("", "新增数据" + list.size(),
                            "www.xinjian.xom", "我是新增的数据", DataUtils.getDate()));
                    swipeRefreshLayout.setRefreshing(false);
                    //newsRecyAdapter.notifyDataSetChanged();
                    mBookends.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            ArrayList<NewsEntity> list2=savedInstanceState.getParcelableArrayList("listString");
            Log.e("ContactsFragment","数据长度为："+list2.size());
        }
        ViewGroup contactsLayout = (ViewGroup) inflater.inflate(R.layout.contacts_fragment,
                container, false);
        recyclerView = (android.support.v7.widget.RecyclerView) contactsLayout.findViewById(R.id.news_recyclerView);
        recyclerView.setHasFixedSize(true);
        newsRecyAdapter = new NewsRecyAdapter(getContext(), list, R.layout.news_fragment_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setItemAnimator(new DefaultItemAnimator());

        mBookends = new Bookends<>(newsRecyAdapter);

        if(headerGroup==null){
            headerGroup= (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.header_list_layout, null, false);
        }
        ViewGroup parent = (ViewGroup) headerGroup.getParent();
        if (parent != null)
        {
            parent.removeView(headerGroup);
        }
        mImageCycleView= (AutoPlayViewPage) headerGroup.findViewById(R.id.news_auto_play);
        mImageCycleView.loadData(listTest);
        mBookends.addHeader(headerGroup);
//        recyclerView.setAdapter(newsRecyAdapter);
        recyclerView.setAdapter(mBookends);

        swipeRefreshLayout = (SwipeRefreshLayout) contactsLayout.findViewById(R.id.swipe_refresh_layout);
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
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return contactsLayout;
    }

    private void initdata() {
            list.add(new NewsEntity("http://f.hiphotos.baidu.com/baike/w%3D268/sign=5677eff4a51ea8d38a227302af0b30cf/7c1ed21b0ef41bd5c8e3b0bb52da81cb38db3dc8.jpg", "小白", "www.小白.com", "今天阳光明媚", DataUtils.getDate()));
            list.add(new NewsEntity("http://img1.3lian.com/img2008/06/019/ych.jpg", "晓明", "www.晓明.com", "我在嘉兴", DataUtils.getDate()));
            list.add(new NewsEntity("http://f.hiphotos.baidu.com/baike/w%3D268/sign=ea19af2a9916fdfad86cc1e88c8e8cea/32fa828ba61ea8d3b0e92005930a304e241f58e3.jpg", "叶凡", "www.叶凡.com", "想念在地球的日子", DataUtils.getDate()));
            list.add(new NewsEntity("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1457077286&di=71fdcab890dbdf3d1279179e3abf40f1&src=http://imgsrc.baidu.com/forum/pic/item/81e0a401a18b87d68d6489c6070828381d30fdc5.jpg", "黑皇", "www.黑皇.com", "本皇要收人宠！", DataUtils.getDate()));

            listTest.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
            listTest.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
            listTest.add(new AutoPlayViewPage.ImageInfo("http://img2.imgtn.bdimg.com/it/u=3922277475,817684749&fm=21&gp=0.jpg", "乐视网TV版大派送", ""));
            listTest.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ContactsFragment", "onCreate");
        initdata();
    }
}
