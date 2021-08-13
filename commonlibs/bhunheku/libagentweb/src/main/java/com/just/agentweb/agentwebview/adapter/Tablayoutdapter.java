package com.just.agentweb.agentwebview.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.just.agentweb.R;
import com.just.agentweb.agentwebview.bean.OneBean1;

public class Tablayoutdapter extends BaseQuickAdapter<OneBean1, BaseViewHolder> {

    public Tablayoutdapter() {
        super(R.layout.recycleview_hxkt_tablayout_item1);
    }

    @Override
    protected void convert(BaseViewHolder helper, OneBean1 item) {
        TextView tv1 = helper.itemView.findViewById(R.id.tv1);
        View view1 = helper.itemView.findViewById(R.id.view1);
        tv1.setText(item.getTab_name());
        if (item.isEnable()) {
            //选中
            view1.setVisibility(View.VISIBLE);
            tv1.setTextColor(ContextCompat.getColor(mContext, R.color.blue55AAFF));
        } else {
            //未选中
            view1.setVisibility(View.GONE);
            tv1.setTextColor(ContextCompat.getColor(mContext, R.color.color999999));
        }
    }


}
