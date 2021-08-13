package com.haier.cellarette.baselibrary.liandong.demo2.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo2.bean.Liandong2CategoryBean;

import java.util.List;

/**
 * Created by lingdong on 2017/12/5.
 */

public class Liandong2CategoryAdapter extends RecyclerView.Adapter<Liandong2CategoryAdapter.LeftViewHolder> {

    private Context mContext;
    private RecyclerView mRcv;
    private List<Liandong2CategoryBean> mData;
    private int checkedItem = -1;                //选中的item项
    private OnItemClickListener mItemClickListener;

    public Liandong2CategoryAdapter(Context mContext, RecyclerView recyclerView, List<Liandong2CategoryBean> mData) {
        this.mContext = mContext;
        this.mRcv = recyclerView;
        this.mData = mData;
    }

    public void updateCheck(int position){
        resetCheck();
        Liandong2CategoryBean bean = mData.get(position);
        bean.setChecked(true);
        mData.set(position,bean);
        checkedItem = position;
        mRcv.smoothScrollToPosition(position);
        notifyDataSetChanged();
    }

    public List<Liandong2CategoryBean> getData() {
        return mData;
    }

    public int getCheckedItem() {
        return checkedItem;
    }

    public void setCheckedItem(int checkedItem) {
        this.checkedItem = checkedItem;
    }

    private void resetCheck(){
        for (int i = 0; i < mData.size(); i++) {
            Liandong2CategoryBean bean = mData.get(i);
            bean.setChecked(false);
            mData.set(i,bean);
        }
    }

    @Override
    public Liandong2CategoryAdapter.LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_liandong2_item_left,parent,false));
    }

    @Override
    public void onBindViewHolder(LeftViewHolder holder, final int position) {
        if(mData.get(position).isChecked()){
            holder.tvCategory.setTextColor(Color.RED);
        }else {
            holder.tvCategory.setTextColor(Color.GRAY);
        }
        holder.tvCategory.setText(mData.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCheck(position);
                if(mItemClickListener !=null){
                    mItemClickListener.onItemClick(view,position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategory;
        public LeftViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
