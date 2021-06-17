package com.fosung.xuanchuanlan.mian.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.mian.http.entity.AppsListReply;

import java.util.List;

public class AppMoreRecyclerViewAdapter extends RecyclerView.Adapter<AppMoreRecyclerViewAdapter.ViewHolder> {
    //数据源
    private List<AppsListReply.DataBean.MenuListBean.AppListBean> mList;
    Context mContext;
    MorOnclickLinstener onlinstener;
    private int mWidth;
    private int height;

    public AppMoreRecyclerViewAdapter(List<AppsListReply.DataBean.MenuListBean.AppListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void setdata(List<AppsListReply.DataBean.MenuListBean.AppListBean> list) {
        mList = list;
    }

    public void reloveLayout(int height, int width) {
        this.height = height;
        this.mWidth = width/4;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appmorerecylerview, parent, false));
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(mList.get(position).name);
            viewHolder.layout.getLayoutParams().width = ScreenUtil.getScreenWidth(ConfApplication.APP_CONTEXT) / 4- DisplayUtil.dip2px(mContext,35);

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
        else  if ("BOX_BMFWZN".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_bmfwzn);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_bmfwzn);
        }
        else  if ("BOX_DSBK".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_dsbk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_dsbk);
        }else if ("BOX_FWCK".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_fwck);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_fwck);
        }else if ("BOX_HSGJX".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_hsgjx);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_hsgjx);
        }else if ("BOX_NOTICE".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_tzgg);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_tzgg);
        }else if ("BOX_NEWS_DYNAMIC".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_xwdt);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_xwdt);
        }
        else if ("BOX_ORGANIZATION".equals(mList.get(position).code)) {
            viewHolder.icon.setImageResource(R.drawable.icon_zzgk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_tzgk);
        }else {
            viewHolder.icon.setImageResource(R.drawable.icon_zzgk);
            viewHolder.layout.setBackgroundResource(R.drawable.bg_tzgk);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlinstener.Onclick(position);
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
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.morelayout);
            icon = (ImageView) itemView.findViewById(R.id.icon_appmore);
            name = (TextView) itemView.findViewById(R.id.appnamemore);
        }
    }

    public interface MorOnclickLinstener {
        void Onclick(int postion);
    }

    public void setLinstener(MorOnclickLinstener linstener) {
        onlinstener = linstener;
    }
}
