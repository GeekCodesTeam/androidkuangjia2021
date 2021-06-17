package com.fosung.xuanchuanlan.mian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.mian.http.entity.VideoDetailBean;

import java.util.List;

public class VideoPubRecyclerViewAdapter extends RecyclerView.Adapter<VideoPubRecyclerViewAdapter.ViewHolder> {
    //数据源
    private List<VideoDetailBean.DataBean.PUBLICBean> mList;

    public VideoPubRecyclerViewAdapter(List<VideoDetailBean.DataBean.PUBLICBean> list) {
        mList = list;
    }

    public void resetSel(int pos) {
        if (mList == null) return;
        for (VideoDetailBean.DataBean.PUBLICBean p : mList) {
            p.isPlay = false;
        }
        if (pos == -1) return;
        for (int i = 0; i < mList.size(); i++) {
            if (i == pos) {
                mList.get(pos).isPlay = true;
                break;
            }
        }
        notifyDataSetChanged();

    }

    public void setdata(List<VideoDetailBean.DataBean.PUBLICBean> list) {
        mList = list;
    }

    OnClicklister onClicklister;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_typerecylerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.typename.setText(mList.get(position).name);
        if (mList.get(position).isPlay) {
            viewHolder.layout.setBackgroundResource(R.color.video_sel);
            viewHolder.ivPlay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layout.setBackgroundResource(android.R.color.transparent);
            viewHolder.ivPlay.setVisibility(View.GONE);
        }
        viewHolder.typename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClicklister != null) {
                    onClicklister.onClick(position);
                }
            }
        });
    }

    //返回item个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView typename;
        public RelativeLayout layout;
        public ImageView ivPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            typename = (TextView) itemView.findViewById(R.id.typename);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_bfz);

        }
    }

    public interface OnClicklister {
        void onClick(int position);
    }

    public void setOnClicklister(OnClicklister onClicklister) {
        this.onClicklister = onClicklister;
    }
}
