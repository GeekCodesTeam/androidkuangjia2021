package com.haier.cellarette.baselibrary.emptyview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haier.cellarette.baselibrary.R;

public class NiubiEmptyView {

    private Context context;
    private View loadingView;
    private TextView loadingView_content;
    private ImageView imageView;
    private View noDataView;
    private TextView noDataView_content;
    private View errorView;
    private TextView errorTextView_content;
    private TextView errorTextView_click;
    private BaseQuickAdapter mAdapter;


    public NiubiEmptyView bind(Context context, View mRecyclerView, BaseQuickAdapter mAdapter) {
        this.context = context;
        this.mAdapter = mAdapter;
//        loadingView = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_recycleviewallsuses_demo8_viewloading, (ViewGroup) mRecyclerView.getParent(), false);
        loadingView = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_network_loading, (ViewGroup) mRecyclerView.getParent(), false);
        loadingView_content = loadingView.findViewById(R.id.loading_notice);
        imageView = loadingView.findViewById(R.id.loading_iv);
        noDataView = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_recycleviewallsuses_demo8_viewnodata, (ViewGroup) mRecyclerView.getParent(), false);
        noDataView_content = noDataView.findViewById(R.id.empty_nodata_notice);
        errorView = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_recycleviewallsuses_demo8_viewerror, (ViewGroup) mRecyclerView.getParent(), false);
        errorTextView_content = errorView.findViewById(R.id.empty_errnet_notice);
        errorTextView_click = errorView.findViewById(R.id.empty_errnet_btn);

        return this;
    }

    public void loading(String loading_text) {
//        mAdapter.setEmptyView(R.layout.activity_recycleviewallsuses_demo8_viewloading, (ViewGroup) mRecyclerView.getParent());
        noDataView_content.setText(TextUtils.isEmpty(loading_text) ? "小象正奔向故事里..." : loading_text);
        imageView.setBackgroundResource(R.drawable.iv_loading_animationlist_slb);
        ((AnimationDrawable) imageView.getBackground()).start();
        mAdapter.setEmptyView(loadingView);
    }

    public void nodata(String nodata_text) {
        noDataView_content.setText(TextUtils.isEmpty(nodata_text) ? "暂无数据" : nodata_text);
        ((AnimationDrawable) imageView.getBackground()).stop();
        mAdapter.setEmptyView(noDataView);
    }

    public void errornet(String error_text) {
        errorTextView_content.setText(TextUtils.isEmpty(error_text) ? "断网了" : error_text);
        ((AnimationDrawable) imageView.getBackground()).stop();
        mAdapter.setEmptyView(errorView);
    }

    public void setRetry(final RetryListener mListener) {
        errorTextView_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.retry();
                }
            }
        });
    }

    private RetryListener mListener;

    public interface RetryListener {
        void retry();
    }

    public void setRetryListener(RetryListener li) {
        this.mListener = li;
    }

}
