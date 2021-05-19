package com.haier.cellarette.activity.indexnew.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shining.baselibrary.toasts.ToastUtil;
import com.example.shining.libwebview.hois2.HiosAlias;
import com.haier.cellarette.R;
import com.haier.cellarette.activity.indexnew.bean.IconBean;
import com.haier.cellarette.activity.indexnew.fragments.SecondIndexFragment;
import com.haier.cellarette.activity.indexnew.util.SelectorUtil;
import com.haier.wifi.base.RecyclerAdapter;

public class SecondIndexFragmentAdapter extends RecyclerAdapter<IconBean> {


    private  Context context;

    public SecondIndexFragmentAdapter(SecondIndexFragment secondIndexFragment) {
        super(R.layout.fragment_secondeindex_adapter_item);
        context = secondIndexFragment.getActivity();
    }

    @Override
    public void onBind(CommHolder holder, int position, int viewType, IconBean data) {
        TextView textView = holder.getView(R.id.tv);
        ImageView imageView = holder.getView(R.id.iv);

        textView.setText(data.getIconName());
        SelectorUtil.addSeletorFromNet(SecondIndexFragmentAdapter.class,data.getDrawbleDown(),data.getDrawbleUp(),imageView,context);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastCenter(position+"");
            }
        });
    }

}
