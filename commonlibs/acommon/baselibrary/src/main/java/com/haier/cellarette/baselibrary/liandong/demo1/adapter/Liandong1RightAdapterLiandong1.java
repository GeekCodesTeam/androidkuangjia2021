package com.haier.cellarette.baselibrary.liandong.demo1.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleRecyclerAdapter;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleViewHolder;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortItem;
import com.haier.cellarette.baselibrary.liandong.demo1.contants.Liandong1ItemType;

/**
 * @author pengbo
 * @date 2019/1/5 0005
 */
public class Liandong1RightAdapterLiandong1 extends Liandong1SimpleRecyclerAdapter<Liandong1SortItem> {


    @Override
    public Liandong1SimpleViewHolder<Liandong1SortItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Liandong1ItemType.BIG_SORT) {
            return new Liandong1RightBigSortViewHolderLiandong1(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_liandong1_recyclerview_item_right_big_sort, parent, false), this);
        } else {
            return new Liandong1RightSmallSortViewHolderLiandong1(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_liandong1_recyclerview_item_right_small_sort, parent, false), this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mListData.get(position).viewType;
    }
}
