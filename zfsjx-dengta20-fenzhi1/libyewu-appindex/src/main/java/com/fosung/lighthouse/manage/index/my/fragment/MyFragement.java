package com.fosung.lighthouse.manage.index.my.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.fosung.lighthouse.manage.common.utils.CommonJumpUtis;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.fosung.lighthouse.manage.index.R;
import com.fosung.lighthouse.manage.index.comact.ScanActivity;
import com.fosung.lighthouse.manage.index.common.IndexConstant;
import com.fosung.lighthouse.manage.index.common.IndexJump;
import com.fosung.lighthouse.manage.index.widget.IndexJobStateDialog;

import com.fosung.lighthouse.manage.myapi.bean.UserLoginBean;
import com.fosung.lighthouse.manage.myapi.presenter.MyPresenter;
import com.fosung.lighthouse.manage.myapi.view.EdcResMyView;
import com.geek.libbase.base.SlbBaseLazyFragmentNew;
import com.geek.libglide47.base.CircleImageView;
import com.geek.libutils.app.MyLogUtil;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zcolin.frame.imageloader.ImageLoaderUtils;

/**
 * @author: lhw
 * @date: 2021/8/2
 * @desc 我的
 */
public class MyFragement extends SlbBaseLazyFragmentNew implements View.OnClickListener, EdcResMyView {

    private SmartRefreshLayout smartRefreshLayout;
    private MaterialHeader materlHeader;
    private TextView tvState, tvSecurity, tvService, tvHelp, tvSetting, tvAbout, tvName, tvDesc;
    private ImageView ivScan;
    private CircleImageView ivHead;

    private MyPresenter mPresenter;

    private IndexJobStateDialog mStateDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index_my;
    }

    @Override
    protected void setup(View rootView, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        smartRefreshLayout = rootView.findViewById(R.id.smartRefresh);
        ivHead = rootView.findViewById(R.id.iv_head);
        tvName = rootView.findViewById(R.id.tv_name);
        tvDesc = rootView.findViewById(R.id.tv_desc);
        tvState = rootView.findViewById(R.id.tv_job_state);
        ivScan = rootView.findViewById(R.id.iv_scan);
        tvSecurity = rootView.findViewById(R.id.tv_security);
        tvService = rootView.findViewById(R.id.tv_service);
        tvHelp = rootView.findViewById(R.id.tv_help);
        tvSetting = rootView.findViewById(R.id.tv_setting);
        tvAbout = rootView.findViewById(R.id.tv_about);
        materlHeader = rootView.findViewById(R.id.materlHeader);
    }

    @Override
    protected void initData() {
        super.initData();
        init();
        setClick();
        doNetwork();
    }


    private void init() {
        mStateDialog = (IndexJobStateDialog) new XPopup.Builder(getContext())
                .enableDrag(true)
                .asCustom(new IndexJobStateDialog(getContext()));


        materlHeader.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPresenter = new MyPresenter(this);


    }

    private void setClick() {
        ivHead.setOnClickListener(this);
        tvState.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        tvSecurity.setOnClickListener(this);
        tvService.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getUserInfo();
            }
        });
    }

    private void doNetwork() {
        mPresenter.getUserInfo();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_head) {
            IndexJump.Companion.jumpLoginAct(getContext());
        } else if (v.getId() == R.id.tv_job_state) {
            mStateDialog.show();
        } else if (v.getId() == R.id.iv_scan) {
            startActivity(new Intent(getContext(), ScanActivity.class));
        } else if (v.getId() == R.id.tv_security) {
            IndexJump.Companion.jumpAccountSecurityAct(getContext());
        } else if (v.getId() == R.id.tv_service) {
            CommonJumpUtis.Companion.jumpCommonWebAct(getContext(), "智能客服", "https://www.baidu.com/");
        } else if (v.getId() == R.id.tv_help) {
            CommonJumpUtis.Companion.jumpCommonWebAct(getContext(), "帮助与反馈", "https://www.baidu.com/");
        } else if (v.getId() == R.id.tv_setting) {
            IndexJump.Companion.jumpSettingAct(getContext());
        } else if (v.getId() == R.id.tv_about) {
            IndexJump.Companion.jumpMyAboutUsAct(getContext());
        }
    }

    @Override
    public void onEduResMySuccess(UserLoginBean bean) {
        smartRefreshLayout.finishRefresh();
        CommonModel.Companion.get().setUsreInfo(bean.data);
        tvName.setText(bean.data.name);
//      tvDesc.setText(bean.data.c);
        ImageLoaderUtils.displayImage(getContext(), bean.data.logo, ivHead);

    }

    @Override
    public void onEduResMyFail(String msg) {
        smartRefreshLayout.finishRefresh();
        ToastUtils.show(msg);
    }

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            try {
//                if ("ShouyeF4".equals(intent.getAction())) {
//                    //TODO 发送广播bufen
//                    Intent msgIntent = new Intent();
//                    msgIntent.setAction("ShouyeF4");
//                    msgIntent.putExtra("query1", 0);
//                    LocalBroadcastManagers.getInstance(getActivity()).sendBroadcast(msgIntent);
//                }
//            } catch (Exception ignored) {
//            }
        }
    }

    @Override
    public void call(Object value) {
//       ToastUtils.showLong("call->" + tablayoutId);
//       MyLogUtil.e("tablayoutId->", "call->" + tablayoutId);

    }

    @Override
    public void onActResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == IndexConstant.SCAN_LOGIN_RESULT_CODE) {
//            ToastUtils.showLong(data.getStringExtra("result"));
        }
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
//        ToastUtils.show();
    }

    /**
     * 当切换底部的时候通知每个fragment切换的id是哪个bufen
     *
     * @param cateId
     */
    public void give_id(String cateId) {
//      ToastUtils.showLong("下拉刷新啦");
        MyLogUtil.e("tablayoutId->", "give_id->" + cateId);
    }

}
