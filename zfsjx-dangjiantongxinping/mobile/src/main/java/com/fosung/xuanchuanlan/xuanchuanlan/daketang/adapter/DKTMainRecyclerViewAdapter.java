package com.fosung.xuanchuanlan.xuanchuanlan.daketang.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity.DKTListContentApply;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

public class DKTMainRecyclerViewAdapter extends BaseRecyclerAdapter<DKTListContentApply.DatalistBean> {

    private final float courseImageScale = 670.0f/400.0f;//省灯塔教育资源库图片
    private Context mContext;

    public DKTMainRecyclerViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_dkt_course_recyclerview;
    }
    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, DKTListContentApply.DatalistBean data) {


        TextView dkt_textview = getView(holder, R.id.dkt_course_name);
        dkt_textview.setText(data.name);

        ImageView dkt_imageview = getView(holder, R.id.dkt_course_icon);
        dkt_imageview.getLayoutParams().height = (int) ((ScreenUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext,60)) /2 /courseImageScale);
        ImageLoaderUtils.displayImage(holder.viewParent.getContext(), data.imgurl, dkt_imageview,R.drawable.course_placeholder);

    }
}
