package com.example.libbase.baserecycleview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.example.libbase.R;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.libbase.utils.CommonUtils;
import com.example.libbase.emptyview.EmptyViewNewNew;
import com.example.libbase.widgets.XRecyclerView;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public abstract class SlbBaseFragmentViewPageYewuList extends SlbBaseLazyFragmentNew {

    //
    protected SmartRefreshLayout refreshLayout1;
    protected ClassicsHeader smartHeader1;
    //
    protected static final int PAGE_SIZE = 10;
    protected int mNextRequestPage = 1;
    protected int mPage;
    protected static boolean mFirstPageNoMore;
    protected static boolean mFirstError = true;
    private int mLastItemVisible;
    private int firstVisibleItemPosition = 0;
    private boolean b;
    //
    protected EmptyViewNewNew emptyview1;
    protected LinearLayout ll_refresh1;
    protected XRecyclerView recyclerView1;
    protected BaseQuickAdapter mAdapter1;
    //
    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (CommonUtils.SlbBaseActivityViewPageAct.equals(intent.getAction())) {
                    //TODO 发送广播bufen
//                    Intent msgIntent = new Intent();
//                    msgIntent.setAction("ShouyeF3");
//                    msgIntent.putExtra("query1", 0);
//                    LocalBroadcastManagers.getInstance(getActivity()).sendBroadcast(msgIntent);
                    //TODO 接受广播bufen
                    String bean = intent.getStringExtra(CommonUtils.SlbBaseActivityViewPageAct1);
                    if (TextUtils.equals(bean, CommonUtils.SlbBaseAct_update)) {
                        // 刷新bufen
                        init_adapter(mAdapter1);
                        retryDataAdd();
                    } else if (TextUtils.equals(bean, CommonUtils.SlbBaseAct_scroll)) {
                        // 滚动bufen
                        if (!b) {
                            if (refreshLayout1 != null) {
                                refreshLayout1.autoRefresh();
                            }
                        } else {
                            recyclerView1.scrollToPosition(0);
                        }
                    } else {
                        // 其他页面通知更新订单状态
                        String id = intent.getStringExtra("id");
                        if (!TextUtils.isEmpty(id)) {
//                        emptyview1.loading();
                            init_adapter(mAdapter1);
                            retryDataAdd();
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManagers.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroyView();
    }

//    @Override
//    public void net_con_none() {
//        emptyview1.errorNet();
//    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        findview(rootView);
    }

    @Override
    protected void initData() {
        super.initData();
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(CommonUtils.SlbBaseActivityViewPageAct);
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        onclick();
    }

    /**
     * 加载更多bufen
     */
    public void loadMore() {
        retryData();
    }


    public void donetwork() {
        donetworkAdd();
        retryData();
    }

    private void retryData() {
        retryDataAdd();
    }

    public void init_adapter(BaseQuickAdapter mAdapter1) {
        if (mAdapter1 == null) {
            return;
        }
        this.mAdapter1 = mAdapter1;
        mNextRequestPage = 1;
        mAdapter1.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter1.setNewData(null);
    }

    public void setData(BaseQuickAdapter mAdapter, boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        // test
        if (mAdapter1.getData().size() > 12) {
            mAdapter1.loadMoreEnd(isRefresh);
            ToastUtils.showLong("已经到底了12");
            return;
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    protected abstract void donetworkAdd();// 第一次加载

    protected abstract void retryDataAdd();// 接口

    protected abstract void onclickAdd();// 事件

    protected abstract void findviewAdd();// 初始化

    protected abstract void emptyviewAdd();// 重试

    protected abstract void refreshLayoutAdd();// 下拉刷新

    private void onclick() {
        smartHeader1.setEnableLastTime(false);
        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                MyLogUtil.e("ssssssssssss", "" + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                dx > 0 时为手指向左滚动,列表滚动显示右面的内容
//                dx < 0 时为手指向右滚动,列表滚动显示左面的内容
//                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
//                dy < 0 时为手指向下滚动,列表滚动显示上面的内容
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                mLastItemVisible = layoutManager.findLastVisibleItemPosition();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                MyLogUtil.e("---geekyun1---", firstVisibleItemPosition + "");
                b = recyclerView.canScrollVertically(-1);
                MyLogUtil.e("---geekyun2---", b + "");
                EventBus.getDefault().post(new Boolean(b));
            }
        });
        mAdapter1.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
                MyLogUtil.e("--geekyun--", "loadMore");
            }
        }, recyclerView1);
        emptyview1.bind(refreshLayout1).setRetryListener(new EmptyViewNewNew.RetryListener() {
            @Override
            public void retry() {
                // 分布局
                emptyviewAdd();
                // 接口bufen
                retryDataAdd();
            }
        });
        refreshLayout1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayoutAdd();
                // 接口bufen
                retryDataAdd();
            }
        });
        onclickAdd();

    }

    private void findview(View rootView) {
        ll_refresh1 = rootView.findViewById(R.id.baserecycleview_ll_refresh1);
        refreshLayout1 = rootView.findViewById(R.id.baserecycleview_refreshLayout1);
        smartHeader1 = rootView.findViewById(R.id.baserecycleview_smart_header1);
        emptyview1 = rootView.findViewById(R.id.baserecycleview_emptyview2);
        recyclerView1 = rootView.findViewById(R.id.baserecycleview_recycler_view1);
        findviewAdd();
    }

    protected <T> void OnOrderSuccess(List<T> mList1) {
        emptyview1.success();
        refreshLayout1.finishRefresh(0);
        if (mNextRequestPage == 1) {
            setData(mAdapter1, true, mList1);
            if (mList1.size() == 0) {
                emptyview1.nodata();
            }
        } else {
            setData(mAdapter1, false, mList1);
        }
    }

    protected void OnOrderNodata() {
        emptyview1.nodata();
        refreshLayout1.finishRefresh(true);
        if (mNextRequestPage == 1) {
            mAdapter1.setEnableLoadMore(true);
//            emptyview1.errorNet();
        } else {
            mAdapter1.loadMoreFail();
        }
    }

    protected void OnOrderFail() {
        emptyview1.errorNet();
        refreshLayout1.finishRefresh(false);
        if (mNextRequestPage == 1) {
            mAdapter1.setEnableLoadMore(true);
            // 根据需求修改bufen
            mAdapter1.setNewData(null);
//            emptyview1.errorNet();
        } else {
            mAdapter1.loadMoreFail();
        }
    }

}
