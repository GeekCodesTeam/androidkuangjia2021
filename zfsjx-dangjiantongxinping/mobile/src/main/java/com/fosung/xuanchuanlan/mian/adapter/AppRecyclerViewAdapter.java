package com.fosung.xuanchuanlan.mian.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.mian.http.entity.AppsListReply;

import java.util.List;

public class AppRecyclerViewAdapter extends RecyclerView.Adapter<AppRecyclerViewAdapter.ViewHolder> {
    //数据源
    private List<AppsListReply.DataBean.MenuListBean.AppListBean> mList;
    Context mContext;
    OnclickListener listener;
    private int height;
    private int width;

    public AppRecyclerViewAdapter(List<AppsListReply.DataBean.MenuListBean.AppListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void setdata(List<AppsListReply.DataBean.MenuListBean.AppListBean> list) {
        mList = list;
    }

    public void reloveLayout(int height, int width) {
        this.height = height;
        this.width = width;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apprecylerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(mList.get(position).name);
        if (height == 0) {
            viewHolder.layout.getLayoutParams().height = ScreenUtil.getScreenWidth(ConfApplication.APP_CONTEXT) / 9;
        } else {
          viewHolder.layout.getLayoutParams().width = width / 2 - DisplayUtil.dip2px(mContext, 10f);
            viewHolder.layout.getLayoutParams().height = height / 2 - DisplayUtil.dip2px(mContext, 10f);
        }
        if ("VIDEO_MEETING_BOX".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_sphy);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_sphy);
        } else if ("face_box".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_rlcj);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_rlcj);
        } else if ("org_life".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zzsh);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_zzsh);
        }
        if ("BOX_DUES_PAY".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.ic_party);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_dfjn);
        }
        if ("BOX_QING_MAO".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_cqcm);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_cqcm);
        }
        if ("BOX_PARTY_EDU".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_dyjy);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_dyjy);
        }
        if ("BOX_LEGAL_AID".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_flyz);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_flyz);
        }
        if ("BOX_LIVE_MEETING".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_hyzb);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_hyzb);
        }
        if ("BOX_TRAFFIC_VIOLATION".equals(mList.get(position).code)) {
            viewHolder.layout.setBackgroundResource(R.drawable.bg_jtwzcx);
            viewHolder.icon.setImageResource(R.drawable.icon_jtwzcx);
        }
        if ("BOX_AGRO_SKILL".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_njpx);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_njpx);
        }
        if ("BOX_SW_PUBLIC".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_swgk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_swgk);
        }
        if ("BOX_SOCIAL_SECURITY".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_sbcx);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_sbcx);
        }
        if ("BOX_DATA_WARNING".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_sjyj);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_sjyj);
        }
        if ("BOX_JUDICIAL_MEDIATION".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_sftj);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_sphy);
        }
        if ("BOX_NON_ATTENDANCE".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_wgkq);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_wgkq);
        }
        if ("BOX_MESSAGE_CENTER".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_xxzx);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_xxzx);
        }
        if ("BOX_NET_VILLAGE".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_ywdc);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_ywdc);
        }
        if ("BOX_POLICY_LAW".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zcfgk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_zcfgk);
        }
        if ("BOX_INTELLIGENT_VILLAGE".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zncz);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_zncz);
        }
        if ("BOX_IMPORTANT_MATTERS".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zdsxjy);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_zdsxjy);
        }
    //e平台
        if ("BOX_BMFWZN".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_bmfwzn);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_bmfwzn);
        }
       if ("BOX_DSBK".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_dsbk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_dsbk);
        }if ("BOX_FWCK".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_fwck);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_fwck);
        }if ("BOX_HSGJX".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_hsgjx);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_hsgjx);
        }if ("BOX_NOTICE".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_tzgg);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_tzgg);
        }if ("BOX_NEWS_DYNAMIC".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_xwdt);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_xwdt);
        }if ("BOX_ORGANIZATION".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zzgk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_tzgk);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Oncklick(position);
            }

        });

    }

    //返回item个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            icon = (ImageView) itemView.findViewById(R.id.icon_app);
            name = (TextView) itemView.findViewById(R.id.appname);
        }
    }

    public interface OnclickListener {
        void Oncklick(int postion);
    }

    public void setOncklick(OnclickListener mlistener) {
        listener = mlistener;
    }
}
