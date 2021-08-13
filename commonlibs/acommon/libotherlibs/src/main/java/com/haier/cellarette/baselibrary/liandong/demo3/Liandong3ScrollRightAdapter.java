package com.haier.cellarette.baselibrary.liandong.demo3;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haier.cellarette.baselibrary.R;

import java.util.List;

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class Liandong3ScrollRightAdapter extends BaseSectionQuickAdapter<Liandong3ScrollBean, BaseViewHolder> {

    public Liandong3ScrollRightAdapter(int layoutResId, int sectionHeadResId, List<Liandong3ScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, Liandong3ScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, Liandong3ScrollBean item) {
        Liandong3ScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.right_text, t.getText());
    }
}
