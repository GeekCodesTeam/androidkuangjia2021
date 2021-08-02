package com.example.slbappcomm.pop.anspay;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.slbappcomm.R;
import com.geek.libglide47.base.GlideImageView;

public class PopYanzhengPayAdapter extends BaseQuickAdapter<PopYanzhengPayBean, BaseViewHolder> {

    public PopYanzhengPayAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PopYanzhengPayBean item) {
        GlideImageView iv1_poppay = helper.itemView.findViewById(R.id.iv1_poppay);
        TextView tv1_poppay = helper.itemView.findViewById(R.id.tv1_poppay);
        iv1_poppay.loadLocalImage(item.getAvatar(), R.drawable.ic_def_loading);
        if (item.isRetweet()){
            tv1_poppay.setPressed(true);
        }else {
            tv1_poppay.setPressed(false);
        }
        helper.setText(R.id.tv1_poppay, item.getId()+"");
        helper.addOnClickListener(R.id.iv1_poppay);
        helper.addOnClickListener(R.id.tv1_poppay);
    }


}
