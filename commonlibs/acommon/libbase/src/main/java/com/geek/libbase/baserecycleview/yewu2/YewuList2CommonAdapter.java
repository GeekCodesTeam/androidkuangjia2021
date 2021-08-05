package com.geek.libbase.baserecycleview.yewu2;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.geek.libbase.R;
import com.geek.libbase.baserecycleview.SlbBaseActivityViewPageTabBean1;
import com.geek.libbase.widgets.LxRelativeLayout;

public class YewuList2CommonAdapter extends BaseQuickAdapter<SlbBaseActivityViewPageTabBean1, BaseViewHolder> {

    public YewuList2CommonAdapter() {
        super(R.layout.fragment_baseviewpage_common_fragment1_item1);
    }

    @Override
    protected void convert(BaseViewHolder helper, SlbBaseActivityViewPageTabBean1 item) {
        TextView tv1 = helper.itemView.findViewById(R.id.tv1);
        LxRelativeLayout rl1 = helper.itemView.findViewById(R.id.rl1);
        rl1.setTouch(true);
        helper.addOnClickListener(R.id.rl1);
    }
}