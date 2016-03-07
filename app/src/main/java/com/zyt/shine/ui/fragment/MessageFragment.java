package com.zyt.shine.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyt.shine.R;
import com.zyt.shine.ui.view.AutoPlayViewPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示消息的Fragment
 */
public class MessageFragment extends Fragment {
    private AutoPlayViewPage mImageCycleView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.message_fragment,
                container, false);
        mImageCycleView = (AutoPlayViewPage) messageLayout.findViewById(R.id.icv_topView);
        initdata();
        return messageLayout;
    }

    private void initdata() {
        List<AutoPlayViewPage.ImageInfo> list = new ArrayList<AutoPlayViewPage.ImageInfo>();
        //res图片资源
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.c, "揭秘北京电影如何升级", ""));
        list.add(new AutoPlayViewPage.ImageInfo("http://img2.imgtn.bdimg.com/it/u=3922277475,817684749&fm=21&gp=0.jpg", "乐视网TV版大派送", ""));
        list.add(new AutoPlayViewPage.ImageInfo(R.mipmap.e, "热血屌丝的反杀", ""));
        mImageCycleView.loadData(list);
        mImageCycleView.setOnPageClickListener(new AutoPlayViewPage.OnPageClickListener() {
            @Override
            public void onClick(View imageView, AutoPlayViewPage.ImageInfo imageInfo) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("http://www.baidu.com");
//                intent.setData(content_url);
//                startActivity(intent);
            }
        });
    }
}
