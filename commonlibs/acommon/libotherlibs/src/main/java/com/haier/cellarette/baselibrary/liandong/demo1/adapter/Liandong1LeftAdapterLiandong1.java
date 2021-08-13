package com.haier.cellarette.baselibrary.liandong.demo1.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleRecyclerAdapter;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleViewHolder;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortBean;

/**
 * @author pengbo
 * @date 2019/1/5 0005
 */
public class Liandong1LeftAdapterLiandong1 extends Liandong1SimpleRecyclerAdapter<Liandong1SortBean> {

    private int mSelectedPosition;

    @Override
    public Liandong1SimpleViewHolder<Liandong1SortBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Liandong1LeftViewHolderLiandong1(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_liandong1_recyclerview_item_search_sort_left, parent, false), this);
    }

    public void setSelectedPosition(int position) {
        mListData.get(mSelectedPosition).isSelected = false;
        notifyItemChanged(mSelectedPosition);
        mListData.get(position).isSelected = true;
        notifyItemChanged(position);
        mSelectedPosition = position;
    }
}
