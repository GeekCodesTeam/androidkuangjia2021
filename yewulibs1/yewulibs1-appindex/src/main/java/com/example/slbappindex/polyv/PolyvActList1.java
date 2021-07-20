package com.example.slbappindex.polyv;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.libbase.baserecycleview.SlbBaseActivityViewPage;
import com.example.libbase.baserecycleview.SlbBaseActivityViewPageTabBean1;
import com.example.slbappcomm.utils.CommonUtils;
import com.example.slbappindex.R;
import com.geek.libutils.app.FragmentHelper;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.haier.cellarette.libwebview.hois2.HiosHelper;

import java.util.ArrayList;
import java.util.List;

public class PolyvActList1 extends SlbBaseActivityViewPage implements View.OnClickListener {

//    private HCategoryPresenter presenter1;

    @Override
    protected void onDestroy() {
//        presenter1.onDestory();
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baseviewpage_common_index1;
    }

    @Override
    protected void donetworkAdd() {
        //
        tvCenterContent.setText("保利威直播");
//        presenter1 = new HCategoryPresenter();
//        presenter1.onCreate(this);

    }

    @Override
    protected void retryDataAdd() {
        // 接口
        emptyview.loading();
        test();
//        presenter1.get_category("miniOrderStatus", "1");
    }

    private void test() {
        List<SlbBaseActivityViewPageTabBean1> mDataTablayout = new ArrayList<>();
        mDataTablayout.add(new SlbBaseActivityViewPageTabBean1("LIVE", "正在直播", false));
        mDataTablayout.add(new SlbBaseActivityViewPageTabBean1("INIT", "直播预告", false));
        mDataTablayout.add(new SlbBaseActivityViewPageTabBean1("END", "历史直播", false));
        List<Fragment> mFragmentList = new ArrayList<>();
        for (int i = 0; i < mDataTablayout.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("id", mDataTablayout.get(i).getTab_id());
//            SlbBaseFragmentViewPageFrag1 fragment1 = FragmentHelper.newFragment(SlbBaseFragmentViewPageFrag1.class, bundle);// 测试1
            PolyvFragmentList1 fragment1 = FragmentHelper.newFragment(PolyvFragmentList1.class, bundle);// 测试2
            mFragmentList.add(fragment1);
        }
        OnOrderSuccess(mDataTablayout, mFragmentList);
    }

    @Override
    protected void onclickAdd() {
        tvCenterContent.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        emptyview.notices("暂无订单，去选课中心看看吧…", "没有网络了,检查一下吧", "正在加载....", "");
    }

    @Override
    protected void findviewAdd() {
        tv_right.setVisibility(View.VISIBLE);
//        select_useful(tv_right, R.drawable.ic_zhaoliying);
        tv_right.setText("直播");
    }

    private void select_useful(TextView tv, int drawabless) {
        Drawable drawable = getResources().getDrawable(drawabless);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.baserecycleview_tv_center_content1) {
            Intent msgIntent = new Intent();
            msgIntent.setAction(CommonUtils.SlbBaseActivityViewPageAct);
            if (!isEnscrolly()) {
                // fragment传值 刷新
                msgIntent.putExtra(CommonUtils.SlbBaseActivityViewPageAct1, CommonUtils.SlbBaseAct_update);
                LocalBroadcastManagers.getInstance(PolyvActList1.this).sendBroadcast(msgIntent);
            } else {
                // fragment传值 滚动
                msgIntent.putExtra(CommonUtils.SlbBaseActivityViewPageAct1, CommonUtils.SlbBaseAct_scroll);
                LocalBroadcastManagers.getInstance(PolyvActList1.this).sendBroadcast(msgIntent);
            }
        } else if (view.getId() == R.id.baserecycleview_tv_right1) {
            // 用户协议
            HiosHelper.resolveAd(PolyvActList1.this, this, "http://pc.jiuzhidao.com/");
        } else {

        }
    }

//    @Override
//    public void OnCategorySuccess(HCategoryBean hCategoryBean) {
//        List<OneBean1> mDataTablayout = new ArrayList<>();
//        for (int i = 0; i < hCategoryBean.getList().size(); i++) {
//            mDataTablayout.add(new OneBean1(hCategoryBean.getList().get(i).getCode(), hCategoryBean.getList().get(i).getName(), false));
//        }
//        current_id = mDataTablayout.get(0).getTab_id();
//        List<Fragment> mFragmentList = new ArrayList<>();
//        for (int i = 0; i < mDataTablayout.size(); i++) {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", mDataTablayout.get(i).getTab_id());
//            OrderCommonFragment2 fragment1 = FragmentHelper.newFragment(OrderCommonFragment2.class, bundle);
//            mFragmentList.add(fragment1);
//        }
//        OnOrderSuccess(mDataTablayout, mFragmentList);
//    }
//
//    @Override
//    public void OnCategoryNodata(String s) {
//        OnOrderNodata();
//
//    }
//
//    @Override
//    public void OnCategoryFail(String s) {
//        OnOrderFail();
//
//    }
}
