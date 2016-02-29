package com.zyt.shine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zyt.shine.R;
import com.zyt.shine.view.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示消息的Fragment
 */
public class MessageFragment extends Fragment {
    private ImageCycleView mImageCycleView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.message_fragment,
                container, false);
        mImageCycleView = (ImageCycleView) messageLayout.findViewById(R.id.icv_topView);
        List<ImageCycleView.ImageInfo> list = new ArrayList<ImageCycleView.ImageInfo>();

        //res图片资源
        list.add(new ImageCycleView.ImageInfo(R.drawable.b, "扑树又回来啦！再唱经典老歌引万人大合唱", ""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.c, "揭秘北京电影如何升级", ""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.d, "乐视网TV版大派送", ""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.e, "热血屌丝的反杀", ""));
        initdata(list);
        return messageLayout;
    }


    void initdata(List<ImageCycleView.ImageInfo> list) {
        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {

                //本地图片
                ImageView imageView = new ImageView(getContext());
//                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                imageView.setImageResource((int) imageInfo.image);
                return imageView;


//				//使用SD卡图片
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageURI(Uri.fromFile((File)imageInfo.image));
//				return smartImageView;

//				//使用SmartImageView，既可以使用网络图片也可以使用本地资源
//				SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//				smartImageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//				return smartImageView;

                //使用BitmapUtils,只能使用网络图片
//				BitmapUtils bitmapUtils = new BitmapUtils(MainActivity.this);
//				ImageView imageView = new ImageView(MainActivity.this);
//				bitmapUtils.display(imageView, imageInfo.image.toString());
//				return imageView;


            }
        });
    }
}
