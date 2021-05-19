package com.haier.cellarette.baselibrary.liandong.demo1.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleRecyclerAdapter;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleViewHolder;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortBean;

/**
 * @author pengbo
 * @date 2019/1/5 0005
 */
public class Liandong1LeftViewHolderLiandong1 extends Liandong1SimpleViewHolder<Liandong1SortBean> {

    /**
     * tvName显示大类名称，view是显示被选中的黄色标记
     */
    private TextView tvName;
    private View view;

    public Liandong1LeftViewHolderLiandong1(View itemView, @Nullable Liandong1SimpleRecyclerAdapter<Liandong1SortBean> adapter) {
        super(itemView, adapter);
        tvName = (TextView) itemView.findViewById(R.id.tv_left);
        view = itemView.findViewById(R.id.view);
    }

    @Override
    protected void refreshView(Liandong1SortBean data) {
        tvName.setText(data.bigSortName);
        //item点击后背景的变化
        if (data.isSelected) {
            view.setVisibility(View.VISIBLE);
            tvName.setBackgroundResource(R.color.color_107);
            tvName.setTextColor(getContext().getResources().getColor(R.color.color_002));
        } else {
            view.setVisibility(View.GONE);
            tvName.setBackgroundResource(R.color.color_109);
            tvName.setTextColor(getContext().getResources().getColor(R.color.color_100));
        }
    }
}
