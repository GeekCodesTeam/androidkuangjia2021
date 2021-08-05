package com.geek.libbase.baserecycleview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.emptyview.NiubiEmptyViewNew;;
import com.geek.libbase.utils.CommonUtils;
import com.geek.libbase.widgets.XRecyclerView;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public abstract class SlbBaseActivityYewuList extends SlbBaseActivity {

    protected ImageView ivBack;
    protected TextView tvCenterContent;
    protected TextView tv_right;
    protected NiubiEmptyViewNew niubiEmptyView;
    protected static final int PAGE_SIZE = 10;
    protected int mNextRequestPage = 1;
    protected int mPage;
    protected static boolean mFirstPageNoMore;
    protected static boolean mFirstError = true;
    protected RefreshLayout refreshLayout1;
    protected ClassicsHeader smartHeader1;
    protected ClassicsFooter smartFooter1;
    protected XRecyclerView recyclerView1;
    protected BaseQuickAdapter mAdapter1;
    protected int mLastItemVisible;
    protected int firstVisibleItemPosition = 0;
    protected boolean b;
    protected boolean enscrolly;

    public boolean isEnscrolly() {
        return enscrolly;
    }

    public void setEnscrolly(boolean enscrolly) {
        this.enscrolly = enscrolly;
    }

    //
    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (CommonUtils.ActYewuList1.equals(intent.getAction())) {
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
    protected void onDestroy() {
        LocalBroadcastManagers.getInstance(this).unregisterReceiver(mMessageReceiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void updateUI(final Boolean scrolly) {
        setEnscrolly(scrolly);
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(CommonUtils.ActYewuList1);
        LocalBroadcastManagers.getInstance(this).registerReceiver(mMessageReceiver, filter);
        niubiEmptyView.loading("");
        donetwork();
    }

    private void findview() {
        refreshLayout1 = findViewById(R.id.baserecycleview_refreshLayout1);
        smartHeader1 = findViewById(R.id.baserecycleview_smart_header1);
        smartFooter1 = findViewById(R.id.baserecycleview_smart_footer1);
        ivBack = findViewById(R.id.baserecycleview_iv_back1);
        tvCenterContent = findViewById(R.id.baserecycleview_tv_center_content1);
        tv_right = findViewById(R.id.baserecycleview_tv_right1);
        recyclerView1 = findViewById(R.id.baserecycleview_recycler_view1);
        findviewAdd();
        niubiEmptyView = new NiubiEmptyViewNew();
        niubiEmptyView.bind(this, recyclerView1, mAdapter1);
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
        if (mAdapter1.getData().size() > 101) {
            mAdapter1.loadMoreEnd(isRefresh);
//            ToastUtils.showLong("已经到底了12");
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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        niubiEmptyView.setRetry(new NiubiEmptyViewNew.RetryListener() {
            @Override
            public void retry() {
                niubiEmptyView.loading("");
                init_adapter(mAdapter1);
                emptyviewAdd();
                // 接口bufen
                retryDataAdd();
            }
        });
        refreshLayout1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                init_adapter(mAdapter1);
                refreshLayoutAdd();
                // 接口bufen
                retryDataAdd();
            }
        });
        onclickAdd();

    }

    protected <T> void OnOrderSuccess(List<T> mList1) {
        refreshLayout1.finishRefresh(0);
        if (mNextRequestPage == 1) {
            setData(mAdapter1, true, mList1);
            if (mList1.size() == 0) {
                niubiEmptyView.errornet(CommonUtils.TIPS_WUSHUJU);
            }
        } else {
            setData(mAdapter1, false, mList1);
        }
    }

    protected void OnOrderNodata() {
        niubiEmptyView.errornet(CommonUtils.TIPS_WUSHUJU);
        refreshLayout1.finishRefresh(true);
        if (mNextRequestPage == 1) {
            mAdapter1.setEnableLoadMore(true);
        } else {
            mAdapter1.loadMoreFail();
        }
    }

    protected void OnOrderFail() {
        niubiEmptyView.errornet(CommonUtils.TIPS_WUWANG);
        refreshLayout1.finishRefresh(false);
        if (mNextRequestPage == 1) {
            mAdapter1.setEnableLoadMore(true);
            // 根据需求修改bufen
//            mAdapter1.setNewData(null);
//            emptyview1.errorNet();
        } else {
            mAdapter1.loadMoreFail();
        }
    }

}
