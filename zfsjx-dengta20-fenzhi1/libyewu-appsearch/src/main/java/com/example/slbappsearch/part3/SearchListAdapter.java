package com.example.slbappsearch.part3;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bizyewu2.bean.SNew1SearchBean1;
import com.example.slbappsearch.R;
import com.geek.libglide47.base.GlideImageView;

public class SearchListAdapter extends BaseQuickAdapter<SNew1SearchBean1, BaseViewHolder> {

    public SearchListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SNew1SearchBean1 item) {
        GlideImageView iv = helper.itemView.findViewById(R.id.iv1);
        iv.loadImage(item.getCoverImg(), R.drawable.ic_def_loading);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            helper.setText(R.id.tv1, Html.fromHtml(item.getTitleHtml(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            helper.setText(R.id.tv1, Html.fromHtml(item.getTitleHtml()));
//        }
        helper.setText(R.id.tv1, item.getTitle());
        helper.setText(R.id.tv2, item.getDescr());
        helper.setText(R.id.tv3, item.getItemCountStr());
        helper.setText(R.id.tv4, item.getViewCountStr());
        //
        GlideImageView iv_bktt1 = helper.itemView.findViewById(R.id.iv_bktt1);
        GlideImageView iv_bktt2 = helper.itemView.findViewById(R.id.iv_bktt2);
        GlideImageView iv_bktt3 = helper.itemView.findViewById(R.id.iv_bktt3);
        GlideImageView iv_bktt4 = helper.itemView.findViewById(R.id.iv_bktt4);
//        (1:左下角,2:右下角,3:右上角,4:左上角)
        if (item.getCornerMap() != null) {
            if (item.getCornerMap().getP1() != null) {
                iv_bktt3.setVisibility(View.VISIBLE);
                iv_bktt3.loadImage(item.getCornerMap().getP1().getImgUrl(), R.color.placeholder_color_transparent);
//                Glide.with(mContext).load(item.getCornerMap().getP1().getImgUrl()).into(iv_bktt3);
            } else {
                iv_bktt3.setVisibility(View.GONE);
                iv_bktt3.loadImage(null, R.color.placeholder_color_transparent);
            }
            if (item.getCornerMap().getP2() != null) {
                iv_bktt4.setVisibility(View.VISIBLE);
                iv_bktt4.loadImage(item.getCornerMap().getP2().getImgUrl(), R.color.placeholder_color_transparent);
//                Glide.with(mContext).load(item.getCornerMap().getP2().getImgUrl()).into(iv_bktt4);
            } else {
                iv_bktt4.setVisibility(View.GONE);
                iv_bktt4.loadImage(null, R.color.placeholder_color_transparent);
            }
            if (item.getCornerMap().getP3() != null) {
                iv_bktt2.setVisibility(View.VISIBLE);
                iv_bktt2.loadImage(item.getCornerMap().getP3().getImgUrl(), R.color.placeholder_color_transparent);
//                Glide.with(mContext).load(item.getCornerMap().getP3().getImgUrl()).into(iv_bktt2);
            } else {
                iv_bktt2.setVisibility(View.GONE);
                iv_bktt2.loadImage(null, R.color.placeholder_color_transparent);
            }
            if (item.getCornerMap().getP4() != null) {
                iv_bktt1.setVisibility(View.VISIBLE);
                iv_bktt1.loadImage(item.getCornerMap().getP4().getImgUrl(), R.color.placeholder_color_transparent);
//                Glide.with(mContext).load(item.getCornerMap().getP4().getImgUrl()).into(iv_bktt1);
            } else {
                iv_bktt1.setVisibility(View.GONE);
                iv_bktt1.loadImage(null, R.color.placeholder_color_transparent);
            }
        }
//        TextView tv_ji11 = helper.itemView.findViewById(R.id.tv_ji11);
//        if (!TextUtils.isEmpty(item.getItemCountStr())) {
//            tv_ji11.setVisibility(View.VISIBLE);
//            tv_ji11.setText(item.getItemCountStr());
//        } else {
//            tv_ji11.setVisibility(View.GONE);
//            tv_ji11.setText("");
//        }

        helper.addOnClickListener(R.id.iv1);
        helper.addOnClickListener(R.id.tv1);
    }

//        "bannerImg": "https://sairobo-edu-elephant.oss-cn-hangzhou.aliyuncs.com/picbook/13/21/coverB.jpg",
//        "bookName": "年的故事",
//        "bookNameHtml": "<font color=\"#f30586\">年</font>的故事",
//        "vertical": false,
//        "readmode": "across",
//        "bookId": "",
//        "bookItemId": "21",
//        "sourceType": "bookItem",
//        "cornerType": ""
//        },


}
