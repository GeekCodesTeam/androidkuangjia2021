package com.example.slbappindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.libbase.widgets.GridSpacingItemDecoration;
import com.example.slbappindex.R;
import com.example.slbappindex.adapters.ShouyeF4Adapter1;
import com.example.slbappindex.adapters.ShouyeF4Bean1;
import com.geek.libglide47.base.util.DisplayUtil;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import java.util.ArrayList;
import java.util.List;

public class ShouyeF4 extends SlbBaseLazyFragmentNew {

    private String tablayoutId;
    private MessageReceiverIndex mMessageReceiver;
    //
    private RecyclerView recyclerView1;
    private ShouyeF4Adapter1 mAdapter1;
    private List<ShouyeF4Bean1> mList1;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF4".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF4");
                    msgIntent.putExtra("query1", 0);
                    LocalBroadcastManagers.getInstance(getActivity()).sendBroadcast(msgIntent);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void call(Object value) {
        tablayoutId = (String) value;
        ToastUtils.showLong("call->" + tablayoutId);
        MyLogUtil.e("tablayoutId->", "call->" + tablayoutId);

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManagers.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        // 从缓存中拿出头像bufen

        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
//        Bundle arg = getArguments();
        if (getArguments() != null) {
            tablayoutId = getArguments().getString("tablayoutId");
            MyLogUtil.e("tablayoutId->", "onCreate->" + tablayoutId);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouyef4;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        recyclerView1 = rootView.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false));
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(4, (int) (DisplayUtil.getScreenWidth(getActivity()) * 8f / 375), true));
        mList1 = new ArrayList<>();
        mAdapter1 = new ShouyeF4Adapter1();
        recyclerView1.setAdapter(mAdapter1);
        //
        onclick();
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF4");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
    }

    private void onclick() {
        mAdapter1.setOnItemClickListener((adapter, view, position) -> {
            //item click
            ToastUtils.showLong(position + "item click");
        });
        mAdapter1.setOnItemChildClickListener((adapter, view, position) -> {
            ShouyeF4Bean1 bean1 = mList1.get(position);
            int i = view.getId();
            if (i == R.id.iv1) {
                if (bean1.getTab_id().contains("http")) {
                    //
                    HiosHelper.resolveAd(getActivity(), getActivity(), bean1.getTab_id());
                } else if (bean1.getTab_id().contains(".hs.act")) {
                    //
//                    Intent intent = new Intent(new Intent(bean1.getTab_id()));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    //
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("dataability://cs.znclass.com/" + AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (bean1.getTab_id().contains("com")) {
                    if (!AppUtils.isAppInstalled(bean1.getTab_id())) {
                        ToastUtils.showLong("未安装此应用服务");
                        return;
                    }
                    AppUtils.launchApp(bean1.getTab_id());
                }
            }
        });
    }

    /**
     * 第一次进来加载bufen
     */
    private void donetwork() {
        retryData();
    }

    // 刷新
    private void retryData() {
        //TODO test
        mList1 = new ArrayList<>();
        mList1.add(new ShouyeF4Bean1("com.tencent.mm", "微信", false));
        mList1.add(new ShouyeF4Bean1(AppUtils.getAppPackageName() + ".hs.act.slbapp.JavaDemoActivity", "灯塔通平台", false));
        mList1.add(new ShouyeF4Bean1("com.fosung.lighthouse", "灯塔2.0", false));
        mList1.add(new ShouyeF4Bean1("http://zwfw.sd.gov.cn", "华为会议", false));
        mList1.add(new ShouyeF4Bean1("com.fosung.lighthouse.ysc", "华为智慧屏", false));
        mList1.add(new ShouyeF4Bean1("com.eg.android.AlipayGphone", "支付宝", false));
        mList1.add(new ShouyeF4Bean1("com.taobao.taobao", "淘宝", false));
        mList1.add(new ShouyeF4Bean1("com.sina.weibo", "微博", false));
        mList1.add(new ShouyeF4Bean1("com.tencent.tmgp.sgame", "王者荣耀", false));

        mAdapter1.setNewData(mList1);
    }

    /**
     * 底部点击bufen
     *
     * @param cateId
     * @param isrefresh
     */
    public void getCate(String cateId, boolean isrefresh) {

        if (!isrefresh) {
            // 从缓存中拿出头像bufen

            return;
        }
        ToastUtils.showLong("下拉刷新啦");
    }

    /**
     * 当切换底部的时候通知每个fragment切换的id是哪个bufen
     *
     * @param cateId
     */
    public void give_id(String cateId) {
//        ToastUtils.showLong("下拉刷新啦");
        MyLogUtil.e("tablayoutId->", "give_id->" + cateId);
    }

    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */


}
