package com.zyt.shine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.yalantis.phoenix.PullToRefreshView;
import com.zyt.shine.R;
import com.zyt.shine.view.AutoPlayViewPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示动态的Fragment
 */
public class NewsFragment extends Fragment {

    private final long REFRESH_DELAY = 2000;

    private AutoPlayViewPage mImageCycleView;
    private PullToRefreshView mPullToRefreshView;

    ListView listView;
    String arr[] = {"1234", "56789", "dsad", "asduwyah", "1234", "56789", "dsad",
            "asduwyah", "1234", "56789", "dsad", "asduwyah"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup newsLayout = (ViewGroup) inflater.inflate(R.layout.news_fragment, container,
                false);
        listView= (ListView) newsLayout.findViewById(R.id.list_view);
        //listView.addHeaderView(inflater.inflate(R.layout.header_list_layout, null),null,false);
        listView.addHeaderView(inflater.inflate(R.layout.header_list_layout, null));
        mImageCycleView=(AutoPlayViewPage) newsLayout.findViewById(R.id.news_auto_play);
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,arr));
        mPullToRefreshView= (PullToRefreshView) newsLayout.findViewById(R.id.pull_to_refresh);

        afterViews();
        initdata();
        return newsLayout;
    }

    protected void afterViews() {
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }
    void initdata() {
    List<AutoPlayViewPage.ImageInfo> list = new ArrayList<AutoPlayViewPage.ImageInfo>();

    //res图片资源
    list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
    list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
    list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.d, "乐视网TV版大派送", ""));
    list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
    mImageCycleView.loadData(list, new AutoPlayViewPage.LoadImageCallBack() {
        @Override
        public ImageView loadAndDisplay(AutoPlayViewPage.ImageInfo imageInfo) {

            //本地图片
            ImageView imageView = new ImageView(getContext());
            //imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
            imageView.setImageResource((int) imageInfo.image);
            return imageView;
        }
    });
    mImageCycleView.setOnPageClickListener(new AutoPlayViewPage.OnPageClickListener() {
        @Override
        public void onClick(View imageView, AutoPlayViewPage.ImageInfo imageInfo) {
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse("http://www.baidu.com");
//            intent.setData(content_url);
//            startActivity(intent);
        }
    });

}
}
