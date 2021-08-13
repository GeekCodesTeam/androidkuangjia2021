package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.eduapi.bean.EduResScreenPopBean;
import com.fosung.eduapi.bean.EduResourceListBean;
import com.fosung.eduapi.presenter.EduResPresenter;
import com.fosung.eduapi.view.EduResListView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourcesListAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.EduScreenPopupView;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.EduUserInfoPopupView;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
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
import java.util.Objects;

@ActivityParam(isShowToolBar = false, isImmerse = false)
public class JiaoyuziyuanAct extends BaseActivity implements View.OnClickListener,EduResListView {

    private String[] strings = new String[]{
            "com.fosung.lighthouse.jiaoyuziyuan.DefaultAlias"};

    private EduResourcesListAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private TextView tv_org_name;
    private TextView tv_user_name;
    private TextView tv_screen;
    private TextView tv_status;
    private EditText edit_query;
    private RadioGroup rg_res_type;
    private BasePopupView statusPop;
    private EduScreenPopupView popupView;
    private EduResPresenter eduResPresenter;
    private int mPage = 0;

    private String resType = "";
    private String auditStatus = "TO_AUDIT";
    private EduResScreenPopBean eduResScreenPopBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edu_resources);
        rg_res_type = getView(R.id.rg_res_type);
        zRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(this)
                .inflate(R.layout.view_pullrecycler_empty, null));

        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name.setText(CommonModel.Companion.get().getUserInfo().name);
        tv_org_name = findViewById(R.id.tv_org_name);
        tv_org_name.setText((Objects.requireNonNull(CommonModel.Companion.get().getRolerBean())).manageName);
        tv_org_name.setOnClickListener(this);

        tv_screen = findViewById(R.id.tv_screen);
        tv_screen.setOnClickListener(this);

        tv_status = findViewById(R.id.tv_status);
        tv_status.setOnClickListener(this);

        edit_query = findViewById(R.id.edit_query);
        edit_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.EduResSearchListAct");
                    intent.putExtra("text",edit_query.getText().toString());
                    intent.putExtra("auditStatus",auditStatus);
                    startActivity (intent);
                    return true;
                }
                return false;
            }
        });

        initPopViews();

        eduResPresenter = new EduResPresenter();
        eduResPresenter.onCreate(this);

        initPullLoad();

    }

    private void initPopViews() {

        statusPop = new XPopup.Builder(this)
                .asCenterList("请选择", new String[]{"待审核", "已审核"},
                        null, 0,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    auditStatus = "TO_AUDIT";
                                } else {
                                    auditStatus = "APPROVED";
                                }
                                zRecyclerView.refreshWithPull();
                            }
                        });

        popupView = new EduScreenPopupView(this, auditStatus);
        popupView.setEduScrennOnSubmitListener(new EduScreenPopupView.EduScrennOnSubmitListener() {
            @Override
            public void OnEduResSubmit(EduResScreenPopBean popBean) {
                eduResScreenPopBean = popBean;
                zRecyclerView.refreshWithPull();
            }
        });

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
    }

    private void initPullLoad() {
        zRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {
            @Override
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
        eduResPresenter.getEduReListRequest(
                auditStatus,
                (Objects.requireNonNull(CommonModel.Companion.get().getRolerBean())).manageId,
                mPage, 15,
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.terminalId),
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.status),
                null,
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.startTime),
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.endTime),
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.startAuditTime),
                (eduResScreenPopBean == null ? null : eduResScreenPopBean.endAuditTime),
                 resType,
                CommonModel.Companion.get().getUserInfo().userId,
                CommonModel.Companion.get().getUserInfo().userId);

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
                intent.putExtra("id",data.getResourceId());
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
                    //半透明阴影背
                    .hasShadowBg(true)
                    .moveUpToKeyboard(true)
                    .autoFocusEditText(false)
                    .asCustom(popupView).show();

        } else if (id == R.id.tv_status) {
            statusPop.show();
        } else if (id == R.id.tv_org_name) {
            new XPopup.Builder(this)
                    .hasShadowBg(true)
                    .moveUpToKeyboard(true)
                    .autoFocusEditText(false)
                    .asCustom(new EduUserInfoPopupView(this)).show();
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
        ToastUtils.show(msg);
    }
}