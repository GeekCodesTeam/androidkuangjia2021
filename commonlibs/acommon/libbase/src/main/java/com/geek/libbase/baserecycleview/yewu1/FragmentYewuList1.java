package com.geek.libbase.baserecycleview.yewu1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geek.libbase.R;
import com.geek.libbase.baserecycleview.SlbBaseActivityViewPageTabBean1;
import com.geek.libbase.baserecycleview.SlbBaseFragmentViewPageYewuList;
import com.geek.libutils.app.MyLogUtil;

import java.util.ArrayList;
import java.util.List;

public class FragmentYewuList1 extends SlbBaseFragmentViewPageYewuList {

    //    private HMyorderPresenter presenter1;
    //
    private YewuList1CommonAdapter mAdapter1;
    private List<SlbBaseActivityViewPageTabBean1> mList1;
    private String tablayoutId;

    @Override
    public void call(Object value) {
        tablayoutId = (String) value;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
//        Bundle arg = getArguments();
        if (getArguments() != null) {
            tablayoutId = getArguments().getString("id");
            MyLogUtil.e("---tablayoutId----", tablayoutId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmentyewulist1_common1;
    }

    @Override
    protected void donetworkAdd() {
        emptyview1.loading();
        init_adapter(mAdapter1);
        // 接口初始化bufen
//        if (presenter1 == null) {
//            presenter1 = new HMyorderPresenter();
//            presenter1.onCreate(this);
//        }
    }

    @Override
    protected void retryDataAdd() {
        String limit = PAGE_SIZE + "";
        String page = mNextRequestPage + "";
        String orderStatus = tablayoutId;
        MyLogUtil.e("--FragmentYewuList1-tablayoutId----", tablayoutId);
        // 接口bufen
//        presenter1.get_myorderlist2(page, limit, orderStatus);
        // test
        mList1 = new ArrayList<>();
////        mList1 = hMyorderBean.getList();
        for (int i = 0; i < 10; i++) {
            mList1.add(new SlbBaseActivityViewPageTabBean1(i + "1", "geek" + i, false));
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                OnOrderSuccess(mList1);
            }
        },3000);
    }

    @Override
    protected void refreshLayoutAdd() {
        // 业务bufen
        init_adapter(mAdapter1);
//      mAdapter1.cancle_timer_all();
    }

    @Override
    protected void emptyviewAdd() {
        // 业务bufen
        emptyview1.loading();
        init_adapter(mAdapter1);
//      mAdapter1.cancle_timer_all();
    }

    @Override
    protected void onclickAdd() {
        mAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SlbBaseActivityViewPageTabBean1 bean = (SlbBaseActivityViewPageTabBean1) adapter.getData().get(position);
                //订单详情
//                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.OrderDetailActivity");
//                intent.putExtra("orderType", bean.getOrderType());
//                intent.putExtra("orderId", bean.getId());
//                startActivity(intent);
            }
        });
        mAdapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final SlbBaseActivityViewPageTabBean1 bean = (SlbBaseActivityViewPageTabBean1) adapter.getData().get(position);
                int i = view.getId();
                if (i == R.id.tv1) {
                    // 入学通知书
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NoticeDetailActivity");
//                    intent.putExtra("orderType", bean.getOrderType());
//                    intent.putExtra("orderId", bean.getId());
//                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void findviewAdd() {
        emptyview1.notices("暂无订单，去选课中心看看吧…", "没有网络了,检查一下吧", "正在加载....", "");
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        mList1 = new ArrayList<>();
        mAdapter1 = new YewuList1CommonAdapter();
        recyclerView1.setAdapter(mAdapter1);
    }

    @Override
    public void onDestroyView() {
//        if (presenter1 != null) {
//            presenter1.onDestory();
//        }
//        mAdapter1.cancle_timer_all();// 业务
        MyLogUtil.e("sssssss", "onDestroy");
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        // 从缓存中拿出头像bufen
        MyLogUtil.e("sssssss", "onResume");
        super.onResume();
    }

    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */

//    @Override
//    public void OnMyorderNewSuccess(SlbBaseActivityViewPageTabBean1 hMyorderBean) {
//        mList1 = new ArrayList<>();
////        mList1 = hMyorderBean.getList();
//        OnOrderSuccess(mList1);
//    }
//
//    @Override
//    public void OnMyorderNewNodata(String s) {
//        OnOrderNodata();
//
//    }
//
//    @Override
//    public void OnMyorderNewFail(String s) {
//        OnOrderFail();
//    }

}
