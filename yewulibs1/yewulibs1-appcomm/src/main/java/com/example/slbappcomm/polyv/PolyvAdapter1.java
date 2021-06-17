package com.example.slbappcomm.polyv;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bizyewu2.bean.SPolyvList1Bean1;
import com.example.libbase.widgets.LxRelativeLayout;
import com.example.slbappcomm.R;
import com.geek.libglide47.base.GlideImageView;

public class PolyvAdapter1 extends BaseQuickAdapter<SPolyvList1Bean1, BaseViewHolder> {

    public PolyvAdapter1() {
        super(R.layout.polyv_fragment1_item1);
    }

    @Override
    protected void convert(BaseViewHolder helper, SPolyvList1Bean1 item) {
        GlideImageView iv1 = helper.itemView.findViewById(R.id.iv1);
        TextView tv1 = helper.itemView.findViewById(R.id.tv1);
        LxRelativeLayout rl1 = helper.itemView.findViewById(R.id.rl1);
        rl1.setTouch(false);
        iv1.loadImage(item.getCoverImg(), R.drawable.ic_default_image);
        tv1.setText(item.getLiveName());
//        helper.addOnClickListener(R.id.rl1);
        helper.addOnClickListener(R.id.iv1);
        helper.addOnClickListener(R.id.tv1);
    }
}