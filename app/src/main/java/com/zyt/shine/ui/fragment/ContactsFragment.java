package com.zyt.shine.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyt.shine.R;
import com.zyt.shine.entity.NewsEntity;
import com.zyt.shine.ui.adapter.NewsRecyAdapter;
import com.zyt.shine.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于展示联系人的Fragment
 */
public class ContactsFragment extends Fragment {
	android.support.v7.widget.RecyclerView recyclerView;
List<NewsEntity> list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.contacts_fragment,
				container, false);
		initdata();
		recyclerView= (android.support.v7.widget.RecyclerView) contactsLayout.findViewById(R.id.news_recyclerView);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(new NewsRecyAdapter(getContext(),list, R.layout.news_fragment_item));
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		return contactsLayout;
	}
	private void initdata(){
		list=new ArrayList<NewsEntity>();
		for(int i=0;i<5;i++){
			list.add(new NewsEntity("http://f.hiphotos.baidu.com/baike/w%3D268/sign=5677eff4a51ea8d38a227302af0b30cf/7c1ed21b0ef41bd5c8e3b0bb52da81cb38db3dc8.jpg","小白","www.小白.com","今天阳光明媚", DataUtils.getDate()));
			list.add(new NewsEntity("http://img1.3lian.com/img2008/06/019/ych.jpg","晓明","www.晓明.com","我在嘉兴",DataUtils.getDate()));
			list.add(new NewsEntity("http://f.hiphotos.baidu.com/baike/w%3D268/sign=ea19af2a9916fdfad86cc1e88c8e8cea/32fa828ba61ea8d3b0e92005930a304e241f58e3.jpg","叶凡","www.叶凡.com","想念在地球的日子",DataUtils.getDate()));
			list.add(new NewsEntity("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1457077286&di=71fdcab890dbdf3d1279179e3abf40f1&src=http://imgsrc.baidu.com/forum/pic/item/81e0a401a18b87d68d6489c6070828381d30fdc5.jpg","黑皇","www.黑皇.com","本皇要收人宠！",DataUtils.getDate()));
		}
	}

}
