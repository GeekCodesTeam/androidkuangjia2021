package com.example.slbappindex.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappindex.R;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.baselibrary.zothers.ComeraAutomation;

public class ShouyeF3 extends SlbBaseLazyFragmentNew {

    private String tablayoutId;
    private TextView tv_center_content;
    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("ShouyeF3".equals(intent.getAction())) {
                    //TODO 发送广播bufen
                    Intent msgIntent = new Intent();
                    msgIntent.setAction("ShouyeF3");
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
        return R.layout.fragment_shouyef3;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tv_center_content = rootView.findViewById(R.id.shouyef3_tv1);
        tv_center_content.setText("业务");
        tv_center_content.setVisibility(View.GONE);
        test(rootView);
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("ShouyeF3");
        LocalBroadcastManagers.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
        donetwork();
//        ShouyeFooterBean bean = new Gson().fromJson("{}", ShouyeFooterBean.class);
//        MyLogUtil.e("sssssssssss", bean.toString());
//        MyLogUtil.e("sssssssssss", bean.getText_id());
//        tv_center_content.setText(bean.getText_id());
    }

    /**
     * 第一次进来加载bufen
     */
    private void donetwork() {
        retryData();
    }

    // 刷新
    private void retryData() {
//        mEmptyView.loading();
//        presenter1.getLBBannerData("0");
//        refreshLayout1.finishRefresh();
//        emptyview1.success();
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

    private void test(View rootView) {
        rootView.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActViewPageYewuList1"));
            }
        });
        rootView.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewuList1"));
            }
        });
        rootView.findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ActYewu3"));
            }
        });
        rootView.findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NkMainActivity"));
            }
        });
        rootView.findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComeraAutomation.getInstance().initCamera(getActivity());
            }
        });
        rootView.findViewById(R.id.tv6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SearchListActivity");
                intent.putExtra("search_key", "搜索");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PLVEntranceActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.JZVideoMainActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.GSYMainActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv91).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.DKMainActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.IMSplashActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.TencentIMSplashActivity");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.RongCloudAct");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PolyvActList1");
                startActivity(intent);
            }
        });
        rootView.findViewById(R.id.tv14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.XpopupMainActivity"));
            }
        });
    }

}
