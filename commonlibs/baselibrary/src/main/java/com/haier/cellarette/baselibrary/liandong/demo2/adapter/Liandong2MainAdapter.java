package com.haier.cellarette.baselibrary.liandong.demo2.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo2.bean.Liandong2FoodBean;

import java.util.List;

/**
 * Created by lingdong on 2017/12/5.
 */

public class Liandong2MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_GROUP_TITLE = 0;
    public static final int TYPE_MAIN_TITLE = 1;
    private Context mContext;
    private List<Liandong2FoodBean> mData;
    private RecyclerView mRcv;
    private boolean move;
    private int index = -1;

    public Liandong2MainAdapter(Context mContext, RecyclerView recyclerView, List<Liandong2FoodBean> mData) {
        this.mContext = mContext;
        this.mRcv = recyclerView;
        this.mData = mData;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getIndex() {
        return index;
    }

    public List<Liandong2FoodBean> getData() {
        return mData;
    }

    public void updatePosition(int groupId) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getGroupId() && mData.get(i).getGroup() == groupId) {
                //mRcv.scrollToPosition(i);
                Log.e("updatePosition", "updatePosition: "+i );
                // 滚到最顶端
                this.index = i;
                LinearLayoutManager manager = (LinearLayoutManager) mRcv.getLayoutManager();
                int firstItem = manager.findFirstVisibleItemPosition();
                int lastItem = manager.findLastVisibleItemPosition();
                if(i <= firstItem){
                    //当要置顶的项在当前显示的第一个项的前面时
                    mRcv.scrollToPosition(i);
                }else if(i<=lastItem){
                    move = true;
                    //当要置顶的项已经在屏幕上显示时
                    int top = mRcv.getChildAt(i-firstItem).getTop();
                    mRcv.scrollBy(0,top);
                    move = false;
                }else {//需要二次滑动
                    mRcv.scrollToPosition(i);
                    move = true;
                }
                break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GROUP_TITLE) {
            return new GroupViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_liandong2_item_group, parent, false));
        } else {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_liandong2_item_main, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder) {
            MainViewHolder viewHolder = (MainViewHolder) holder;
            viewHolder.ivMain.setImageResource(mData.get(position).getImageId());
            viewHolder.tvMain.setText(mData.get(position).getTitle());
        } else {
            GroupViewHolder viewHolder = (GroupViewHolder) holder;
            viewHolder.tvGroup.setText(mData.get(position).getGroupTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getGroupId()) {
            return TYPE_GROUP_TITLE;
        } else {
            return TYPE_MAIN_TITLE;
        }
    }


    public static class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMain;
        TextView tvMain;

        public MainViewHolder(View itemView) {
            super(itemView);
            ivMain = itemView.findViewById(R.id.iv_main);
            tvMain = itemView.findViewById(R.id.tv_main);
        }
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroup;

        public GroupViewHolder(View itemView) {
            super(itemView);
            tvGroup = itemView.findViewById(R.id.tv_group);
        }
    }
}
