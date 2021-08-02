package com.fosung.xuanchuanlan.mian.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.mian.http.entity.VoidListReply;

import java.util.List;

public class VoidRecyclerViewAdapter extends RecyclerView.Adapter<VoidRecyclerViewAdapter.ViewHolder>{
    //数据源
    private List<VoidListReply.DatalistBean> mList;
    Context mcontext;
    OnClickListener onClickListener;
    public VoidRecyclerViewAdapter(List<VoidListReply.DatalistBean> list,Context context) {
        mList = list;
        mcontext=context;
    }
    public void setdata(List<VoidListReply.DatalistBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_void_recylerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.typename.setText(mList.get(position).videoSub);
        viewHolder.onlincount.setText("开始时间:"+mList.get(position).hopeStartDate);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onclick(position);
            }
        });
    }
    //返回item个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView typename;
        public TextView onlincount;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            typename = (CheckedTextView) itemView.findViewById(R.id.typename);
            onlincount = (TextView) itemView.findViewById(R.id.count);
        }
    }
    public  interface OnClickListener{
        void onclick(int postion);
    }
    public void setOnclick(OnClickListener listener){
        onClickListener=listener;
    }
}
