package com.haier.cellarette.baselibrary.liandong.demo1.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleRecyclerAdapter;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleViewHolder;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortItem;

/**
 * @author pengbo
 * @date 2019/1/5 0005
 */
public class Liandong1RightSmallSortViewHolderLiandong1 extends Liandong1SimpleViewHolder<Liandong1SortItem> {

    private TextView textView;

    public Liandong1RightSmallSortViewHolderLiandong1(View itemView, @Nullable Liandong1SimpleRecyclerAdapter<Liandong1SortItem> adapter) {
        super(itemView, adapter);
        textView = itemView.findViewById(R.id.tv_small);
    }

    @Override
    protected void refreshView(Liandong1SortItem data) {
        textView.setText(data.name);
    }
}
