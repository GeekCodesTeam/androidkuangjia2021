package com.example.slbappcomm.baserecycleview.yewu1;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.slbappcomm.R;
import com.example.slbappcomm.baserecycleview.SlbBaseActivityViewPageTabBean1;
import com.haier.cellarette.baselibrary.widget.LxRelativeLayout;

public class YewuList1CommonAdapter extends BaseQuickAdapter<SlbBaseActivityViewPageTabBean1, BaseViewHolder> {

    public YewuList1CommonAdapter() {
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