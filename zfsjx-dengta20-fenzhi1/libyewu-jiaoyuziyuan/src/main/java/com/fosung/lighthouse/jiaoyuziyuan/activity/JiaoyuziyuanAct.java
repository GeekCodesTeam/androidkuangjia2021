package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.eduapi.bean.EduResourceListBean;
import com.fosung.eduapi.presenter.EduResPresenter;
import com.fosung.eduapi.view.EduResListView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourcesListAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.EduScreenPopupView;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;
import java.util.List;

@ActivityParam(isShowToolBar = false, isImmerse = false)
public class JiaoyuziyuanAct extends BaseActivity implements View.OnClickListener,EduResListView {

    private String[] strings = new String[]{
            "com.fosung.lighthouse.jiaoyuziyuan.DefaultAlias"};

    private EduResourcesListAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private TextView tv_screen;
    private TextView tv_status;
    private TextView tv_org_name;
    private RadioGroup rg_res_type;
    private BasePopupView statusPop;
    private EduResPresenter eduResPresenter;
    private int mPage = 0;//当前页

    private String resType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edu_resources);
        rg_res_type = getView(R.id.rg_res_type);
        zRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(this)
                .inflate(R.layout.view_pullrecycler_empty, null));//setEmptyView

        tv_screen = findViewById(R.id.tv_screen);
        tv_screen.setOnClickListener(this);

        tv_status = findViewById(R.id.tv_status);
        tv_status.setOnClickListener(this);

        statusPop = new XPopup.Builder(this)
                .asCenterList("请选择", new String[]{"全部", "待审核", "已审核"},
                        null, 1,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                ToastUtils.show(text);
                            }
                        });
        tv_org_name = findViewById(R.id.tv_org_name);
        tv_org_name.setOnClickListener(this);

        eduResPresenter = new EduResPresenter();
        eduResPresenter.onCreate(this);

        rg_res_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_all) {
                    resType = "";
                } else if (i == R.id.btn_video) {
                    resType = "VIDEO";
                } else if (i == R.id.btn_audio) {
                    resType = "AUDIO";
                } else if (i == R.id.btn_text) {
                    resType = "IMAGE";
                }
                zRecyclerView.refreshWithPull();
            }
        });

        zRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {
            public void onRefresh() {
                mPage = 0;
                zRecyclerView.setNoMore(false);
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });

        zRecyclerView.refreshWithPull();
    }

    private void getData() {
        List<String> list = new ArrayList<>();
        eduResPresenter.getEduReListRequest(
                "TO_AUDIT",
                "030b9e46-b8ea-47ec-9feb-fb8c3eead801",
                mPage, 15,
                list, resType,
                "6330562072407953408",
                "6330562072407953408");

    }

    public void setDataToRecyclerView(List<EduResourceListBean.DatalistBean> list) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduResourcesListAdapter();
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        if (mPage == 0) {
            mRecyclerViewAdapter.setDatas(list);
        } else {
            mRecyclerViewAdapter.addDatas(list);
        }

        zRecyclerView.setPullLoadMoreCompleted();

        mRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<EduResourceListBean.DatalistBean>() {
            @Override
            public void onItemClick(View covertView, int position, EduResourceListBean.DatalistBean data) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.EduResourceDetailAct");
                intent.putExtra("id",data.getId());
                intent.putExtra("auditStatus",data.getAuditStatus());
                startActivity (intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_screen) {
            new XPopup.Builder(this)
                    .hasShadowBg(true) //半透明阴影背景
                    .moveUpToKeyboard(true)
                    .autoFocusEditText(false)
                    .asCustom(new EduScreenPopupView(this)).show();
        } else if (id == R.id.tv_status) {
            statusPop.show();
        } else if (id == R.id.tv_org_name) {
//            new XPopup.Builder(this)
//                    .hasShadowBg(true) //半透明阴影背景
//                    .moveUpToKeyboard(true)
//                    .autoFocusEditText(false)
//                    .asCustom(new EduUserInfoPopupView(this)).show();

            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.EduResourceDetailAct");
//            intent.putExtra("id",data.getId());
//            intent.putExtra("auditStatus",data.getAuditStatus());
            startActivity (intent);
        }
    }

    @Override
    public void OnEduResListSuccess(EduResourceListBean bean) {
        setDataToRecyclerView(bean.getDatalist());
        if (mPage == bean.getTotalpages() - 1) {
            zRecyclerView.setNoMore(true);
            zRecyclerView.setIsShowNoMore(false);
        } else {
            mPage += 1;
            zRecyclerView.setNoMore(false);
            zRecyclerView.setIsShowNoMore(true);
        }
    }

    @Override
    public void OnEduResListFail(String msg) {
        setDataToRecyclerView(null);
        ToastUtil.toastShort(msg);
    }
}