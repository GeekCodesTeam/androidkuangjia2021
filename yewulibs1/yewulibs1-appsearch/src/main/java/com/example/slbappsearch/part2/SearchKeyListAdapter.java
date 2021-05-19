package com.example.slbappsearch.part2;

import android.os.Build;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bizyewu2.bean.SNew1SearchBean1;
import com.example.slbappsearch.R;

public class SearchKeyListAdapter extends BaseQuickAdapter<SNew1SearchBean1, BaseViewHolder> {

    public SearchKeyListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SNew1SearchBean1 item) {
//        GlideImageView iv = helper.itemView.findViewById(R.id.iv1);
//        iv.loadImage(item.getBookBigPicture(), R.drawable.ic_def_loading);
//        FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
//        FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper.setText(R.id.tv_search_key, Html.fromHtml(item.getTitleHtml(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            helper.setText(R.id.tv_search_key, Html.fromHtml(item.getTitleHtml()));
        }
        helper.addOnClickListener(R.id.ll_bg1);
    }


}
