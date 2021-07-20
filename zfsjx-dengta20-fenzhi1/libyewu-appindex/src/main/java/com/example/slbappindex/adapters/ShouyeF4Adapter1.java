package com.example.slbappindex.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.slbappindex.R;
import com.geek.libglide47.base.GlideImageView;

public class ShouyeF4Adapter1 extends BaseQuickAdapter<ShouyeF4Bean1, BaseViewHolder> {

    public ShouyeF4Adapter1() {
        super(R.layout.activity_shouyef4_footer_item1);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShouyeF4Bean1 item) {
        GlideImageView iv1 = helper.itemView.findViewById(R.id.iv1);
        TextView tv1 = helper.itemView.findViewById(R.id.tv1);
        iv1.loadImage("", R.drawable.icon_dh1);
        tv1.setText(item.getTab_name());
        helper.addOnClickListener(R.id.iv1);
    }


}