package com.example.slbappindex.polyv;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.easefun.polyv.livedemo.PolyvUtils;
import com.example.bizyewu2.bean.SPolyvList1Bean;
import com.example.bizyewu2.bean.SPolyvList1Bean1;
import com.example.bizyewu2.presenter.HPolyvList1Presenter;
import com.example.bizyewu2.view.HPolyvList1View;
import com.example.libbase.baserecycleview.SlbBaseFragmentViewPageYewuList;
import com.example.slbappcomm.R;
import com.geek.libutils.app.MyLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 保利威直播+接口demo
 */
public class PolyvFragmentList1 extends SlbBaseFragmentViewPageYewuList implements HPolyvList1View {

    private HPolyvList1Presenter presenter1;
    //
    private PolyvAdapter1 mAdapter1;
    private List<SPolyvList1Bean1> mList1;
    private String tablayoutId;
//    private PolyvUtils polyvUtils;
    private String appId = "fdhjtb97mj";
    private String appSecret = "1ec091fc12c94109afbd8ed47c81f78c";
    private String userId = "496bff1348";
    private String channelId = "2163376";

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
        // TODO 初始化polyv
//        polyvUtils = new PolyvUtils();
//        polyvUtils.init(getActivity());
        // test
//        polyvUtils.init(getActivity(), appId, appSecret, userId);
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
        if (presenter1 == null) {
            presenter1 = new HPolyvList1Presenter();
            presenter1.onCreate(this);
        }
    }

    @Override
    protected void retryDataAdd() {
        String limit = PAGE_SIZE + "";
        String page = mNextRequestPage + "";
        String orderStatus = tablayoutId;
        MyLogUtil.e("--FragmentYewuList1-tablayoutId----", tablayoutId);
        // 接口bufen
        presenter1.get_polyv_list1(orderStatus, "3476900205838738445",page, limit);
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
                SPolyvList1Bean1 bean = (SPolyvList1Bean1) adapter.getData().get(position);
                //订单详情
//                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.OrderDetailActivity");
//                intent.putExtra("orderType", bean.getOrderType());
//                intent.putExtra("orderId", bean.getId());
//                startActivity(intent);
                //TODO Polyv
//                polyvUtils.into_polyv(getActivity(), channelId);
//                polyvUtils.into_polyv_liveback(getActivity(), "", "");
            }
        });
        mAdapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final SPolyvList1Bean1 bean = (SPolyvList1Bean1) adapter.getData().get(position);
                int i = view.getId();
                if (i == R.id.iv1) {
                    // 入学通知书
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NoticeDetailActivity");
//                    intent.putExtra("orderType", bean.getOrderType());
//                    intent.putExtra("orderId", bean.getId());
//                    startActivity(intent);
                    //TODO Polyv
//                    polyvUtils.into_local_liveback(getActivity());
                }
                if (i == R.id.tv1) {
                    // 入学通知书
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NoticeDetailActivity");
//                    intent.putExtra("orderType", bean.getOrderType());
//                    intent.putExtra("orderId", bean.getId());
//                    startActivity(intent);
                    //TODO Polyv
//                    polyvUtils.into_polyv(getActivity(), channelId);
                }


            }
        });
    }

    @Override
    protected void findviewAdd() {
        emptyview1.notices("暂无订单，去选课中心看看吧…", "没有网络了,检查一下吧", "正在加载....", "");
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        mList1 = new ArrayList<>();
        mAdapter1 = new PolyvAdapter1();
        recyclerView1.setAdapter(mAdapter1);
    }

    @Override
    public void onDestroyView() {
        if (presenter1 != null) {
            presenter1.onDestory();
        }
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

    @Override
    public void OnPolyvList1Success(SPolyvList1Bean hMyorderBean) {
        mList1 = new ArrayList<>();
//        mList1 = hMyorderBean.getList();
        OnOrderSuccess(mList1);
    }

    @Override
    public void OnPolyvList1Nodata(String s) {
        OnOrderNodata();

    }

    @Override
    public void OnPolyvList1Fail(String s) {
        OnOrderFail();
    }

}
