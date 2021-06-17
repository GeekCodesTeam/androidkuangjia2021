package com.fosung.xuanchuanlan.xuanchuanlan.notice.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeListReply;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

public class RedBackRecyclerViewAdapter extends BaseRecyclerAdapter<NoticeListReply.DatalistBean> {

    private Context mContext;

    public RedBackRecyclerViewAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_redback_recyclerview;
    }

    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, NoticeListReply.DatalistBean data) {

        ImageView dkt_imageview = getView(holder, R.id.dkt_course_icon);
        ImageLoaderUtils.displayImage(holder.viewParent.getContext(), data.img, dkt_imageview);

    }
}
