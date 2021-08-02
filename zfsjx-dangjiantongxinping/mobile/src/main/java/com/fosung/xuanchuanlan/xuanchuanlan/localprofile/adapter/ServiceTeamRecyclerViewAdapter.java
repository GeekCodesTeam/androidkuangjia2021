package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.http.ServiceTeamListReply;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

public class ServiceTeamRecyclerViewAdapter extends BaseRecyclerAdapter<ServiceTeamListReply.DatalistBean> {

    private Context mContext;
    private final float courseImageScale = 300.0f/300.0f;//省灯塔教育资源库图片

    public ServiceTeamRecyclerViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_service_team_recyclerview;
    }
    @Override
    public void setUpData(CommonHolder holder, int position, int viewType, ServiceTeamListReply.DatalistBean data) {

        ImageView headerIM = getView(holder, R.id.id_service_header);
        headerIM.getLayoutParams().height = (int) ((ScreenUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext,60)) /2 /courseImageScale);
        ImageLoaderUtils.displayImage(holder.viewParent.getContext(), data.img, headerIM);

        TextView nameTX = getView(holder, R.id.id_service_name);
        nameTX.setText(data.name);

        TextView positionTX = getView(holder, R.id.id_service_position);
        positionTX.setText(data.position);

        TextView introductionTX = getView(holder, R.id.id_service_info);
        introductionTX.setText("岗位职责：" + data.introduction);

        TextView tellTX = getView(holder, R.id.id_service_tell);
        tellTX.setText("联系方式：" + data.tell);
    }
}
