package com.fosung.xuanchuanlan.xuanchuanlan.notice.adapter;

import android.widget.TextView;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeListReply;
import com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext.RichTextView;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

public class NoticeListRecyclerViewAdapter extends BaseRecyclerAdapter<NoticeListReply.DatalistBean> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_notice_list;
    }

    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, NoticeListReply.DatalistBean data) {

        TextView noticeName = getView(holder, R.id.id_notice_name);
        noticeName.setText(data.title);
        RichTextView noticeContent = getView(holder, R.id.id_notice_content);
        noticeContent.setText(data.introduction);
        TextView noticeTime = getView(holder, R.id.id_notice_time);
        noticeTime.setText(data.pubDatetime);

    }
}
