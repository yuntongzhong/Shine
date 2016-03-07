package com.zyt.shine.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zyt.shine.R;
import com.zyt.shine.entity.NewsEntity;
import com.zyt.shine.glide.GlideCircleTransform;

import java.util.List;

/**
 * Created by zyt on 2016/3/4.
 */
public class NewsRecyAdapter extends RecyclerView.Adapter<NewsRecyAdapter.NewsViewHolder> {
    private List<NewsEntity> items;
    private int itemLayout;
    private Context mContext;

    public NewsRecyAdapter(Context context, List<NewsEntity> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.mContext = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if(position==items.size()){
            holder.recyDivider.setVisibility(View.GONE);
        }
        final NewsEntity item = items.get(position);
        holder.time.setText(item.getReleaseTime());
        holder.name.setText(item.getName());
        holder.msg.setText(item.getMsg());
        Glide.with(mContext).load(item.getIcon())
                .centerCrop()
                .transform(new GlideCircleTransform(mContext))
                .into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "imgClick:"+item.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.getParentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "itemClick:"+item.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView time;
        public TextView msg;
        public View parentView;
        public View recyDivider;

        public NewsViewHolder(View itemView) {
            super(itemView);
            parentView=itemView;
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.user_name);
            time = (TextView) itemView.findViewById(R.id.release_time);
            msg = (TextView) itemView.findViewById(R.id.msg_content);
            recyDivider=itemView.findViewById(R.id.recy_divider);
        }
       public View getParentView(){
           return parentView;
       }
    }
}
