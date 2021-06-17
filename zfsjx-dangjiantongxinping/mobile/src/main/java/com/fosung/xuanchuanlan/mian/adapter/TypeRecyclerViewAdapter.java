package com.fosung.xuanchuanlan.mian.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.mian.http.entity.AppsListReply;

import java.util.List;

public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewAdapter.ViewHolder>{
    //数据源
    private List<AppsListReply.DataBean.MenuListBean> mList;
    public TypeRecyclerViewAdapter(List<AppsListReply.DataBean.MenuListBean> list) {
        mList = list;
    }
    public void setdata(List<AppsListReply.DataBean.MenuListBean> list) {
        mList = list;
        for(int i=0;i<mList.size();i++){
            if(i==0){
                mList.get(i).ischeck=true;
            }else {
                mList.get(i).ischeck=false;
            }
        }
    }

    OnClicklister onClicklister;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typerecylerview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.typename.setText(mList.get(position).name);
        if(mList.get(position).ischeck){
            viewHolder.typename.setBackgroundResource(R.drawable.item_type_pressnew);
        }else {
            viewHolder.typename.setBackgroundResource(R.drawable.item_type_nornalnew);
        }
        viewHolder.typename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClicklister!=null){
                    onClicklister.onClick(position);
                }
                for(int i=0;i<mList.size();i++){
                        mList.get(i).ischeck=false;
                }
                mList.get(position).ischeck=true;
                notifyDataSetChanged();
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

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            typename = (TextView) itemView.findViewById(R.id.typename);

        }
    }
   public interface OnClicklister{
        void onClick(int position);
   }

    public void setOnClicklister(OnClicklister onClicklister) {
        this.onClicklister = onClicklister;
    }
}
