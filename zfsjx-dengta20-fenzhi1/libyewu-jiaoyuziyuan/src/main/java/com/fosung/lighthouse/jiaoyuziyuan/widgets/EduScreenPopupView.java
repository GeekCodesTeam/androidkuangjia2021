package com.fosung.lighthouse.jiaoyuziyuan.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.eduapi.bean.EduResCompanyExecutorReplyBean;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.eduapi.bean.EduResScreenPopBean;
import com.fosung.eduapi.bean.EduResStatusPopBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResCommonEvent;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResCompanySelectedExecutorsUtil;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil;
import com.geek.libglide47.base.util.DisplayUtil;
import com.hjq.toast.ToastUtils;
import com.lib.aliocr.widget.crop.Log;
import com.lxj.xpopup.core.BottomPopupView;
import com.zcolin.frame.util.DateUtils;
import com.zcolin.frame.util.ScreenUtil;
import com.zcolin.gui.ZDialogWheelDate;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EduScreenPopupView extends BottomPopupView implements View.OnClickListener {

    private TextView tv_app;
    private ImageView iv_delete;
    private TextView tv_company;
    private ImageView iv_company_delete;
    private TextView tv_submit;
    private EditText edit_start;
    private EditText edit_end;
    private EditText edit_start_t;
    private EditText edit_end_t;

    private RecyclerView rl_app;
    private BaseRecyclerAdapter adapter;
    private ArrayList<EduResStatusPopBean> datas = new ArrayList();

    private int index = 0;

    private Context context;
    private String auditStatus;
    private List<EduResExecutorReplyBean.DataBean> selectZDList;
    private List<EduResCompanyExecutorReplyBean.DataBean> selectDWList;
    private EduScrennOnSubmitListener eduScrennOnSubmitListener;
    private EduResScreenPopBean popBean;

    public EduScreenPopupView(@NonNull @NotNull Context context, String auditStatus) {
        super(context);
        this.context = context;
        this.auditStatus = auditStatus;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.view_screen_popup;
    }

    public void setEduScrennOnSubmitListener(EduScrennOnSubmitListener eduScrennOnSubmitListener) {
        this.eduScrennOnSubmitListener = eduScrennOnSubmitListener;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        popBean = new EduResScreenPopBean();

        tv_app = findViewById(R.id.tv_app);
        tv_app.setOnClickListener(this);
        iv_delete = findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);

        tv_company = findViewById(R.id.tv_company);
        tv_company.setOnClickListener(this);
        iv_company_delete = findViewById(R.id.iv_company_delete);
        iv_company_delete.setOnClickListener(this);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        tv_submit = findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);

        edit_start = findViewById(R.id.edit_start);
        edit_start.setOnClickListener(this);
        edit_end = findViewById(R.id.edit_end);
        edit_end.setOnClickListener(this);
        edit_start_t = findViewById(R.id.edit_start_t);
        edit_start_t.setOnClickListener(this);
        edit_end_t = findViewById(R.id.edit_end_t);
        edit_end_t.setOnClickListener(this);

        rl_app = findViewById(R.id.rl_app);
        rl_app.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rl_app.setNestedScrollingEnabled(false);
        rl_app.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect,
                                       @NonNull @NotNull View view,
                                       @NonNull @NotNull RecyclerView parent,
                                       @NonNull @NotNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = DisplayUtil.dip2px(getContext(), 5f);
                outRect.bottom = DisplayUtil.dip2px(getContext(), 5f);
            }
        });

        initAdapter();
        rl_app.setAdapter(adapter);
        if ("TO_AUDIT".equals(auditStatus)){
            datas.add(new EduResStatusPopBean("全部",""));
            datas.add(new EduResStatusPopBean("正常","NORMAL"));
            datas.add(new EduResStatusPopBean("禁用","MASK"));

        } else {
            datas.add(new EduResStatusPopBean("全部",""));
            datas.add(new EduResStatusPopBean("审核通过","APPROVED"));
            datas.add(new EduResStatusPopBean("审核通过并终审","PASS_END"));
            datas.add(new EduResStatusPopBean("不采用","NOT_USE"));
            datas.add(new EduResStatusPopBean("退回修改","BACK_EDIT"));
            datas.add(new EduResStatusPopBean("禁用","MASK"));
        }

        adapter.setDatas(datas);

    }

    private void initAdapter() {
        adapter = new BaseRecyclerAdapter<EduResStatusPopBean>() {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_edu_resources_screen_app;
            }

            @Override
            public void setUpData(CommonHolder holder, int position, int viewType, EduResStatusPopBean data) {
                RadioButton btnStatus = getView(holder, R.id.btn_status);
                btnStatus.setText(data.name);
                btnStatus.setOnClickListener(new OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        index = position;
                        popBean.status = data.code;
                        notifyDataSetChanged();
                    }
                });

                btnStatus.setChecked(index == position);
            }
        };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(EduResCommonEvent event) {
        if (event.code == EduResCommonEvent.BSZD) {
            selectZDList = EduResSelectedExecutorsUtil.getInstance(context).getDataList();
            if (selectZDList.size() > 0) {
                tv_app.setText(selectZDList.get(0).name);
                popBean.terminalId = selectZDList.get(0).parentId;
                iv_delete.setVisibility(View.VISIBLE);
            }
        } else if (event.code == EduResCommonEvent.BSDW) {
            selectDWList = EduResCompanySelectedExecutorsUtil.getInstance(context).getDataList();
            if (selectDWList.size() > 0) {
                tv_company.setText(selectDWList.get(0).orgName);
                popBean.createOrgId = selectDWList.get(0).orgId;
                iv_company_delete.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("销毁了...");
        EduResSelectedExecutorsUtil.getInstance(context).clear();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtil.getWindowHeight((Activity) context) * 0.8);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_delete) {
            iv_delete.setVisibility(View.GONE);
            tv_app.setText("全部(点击筛选)");
            EduResSelectedExecutorsUtil.getInstance(context).clear();
        } else if (view.getId() == R.id.tv_app) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp" +
                    ".EduResOrgListAct");
            Bundle bundle = new Bundle();
            bundle.putBoolean("isSingleSelect", true);
            bundle.putBoolean("canSelectDepartment", true);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        }
        if (view.getId() == R.id.iv_company_delete) {
            iv_company_delete.setVisibility(View.GONE);
            tv_company.setText("全部(点击筛选)");
            EduResCompanySelectedExecutorsUtil.getInstance(context).clear();
        } else if (view.getId() == R.id.tv_company) {
            Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp" +
                    ".EduResCompanySelectExecutorListAct");
            Bundle bundle = new Bundle();
            EduResCompanyExecutorReplyBean.DataBean data =
                    new EduResCompanyExecutorReplyBean.DataBean();
//            data.code = XPGZDModel.getInstance().getRoleBean().teamCode;
            data.orgId = "e011ad6a-57f1-11e7-a1a4-0050569a68e4";
            data.orgName = "中共青岛市";

            bundle.putParcelable("org", data);
            bundle.putBoolean("isSingleSelect", true);
            bundle.putBoolean("canSelectDepartment", true);
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        } else if (view.getId() == R.id.tv_submit) {
            this.dismiss();
            if (eduScrennOnSubmitListener != null) {
                eduScrennOnSubmitListener.OnEduResSubmit(popBean);
            }
        } else if (view.getId() == R.id.edit_start) {

            new ZDialogWheelDate(context).setTitle("选择开始时间")
                    .setDataSubmitListener(new ZDialogWheelDate.OnDateSubmitListener() {
                        @Override
                        public void onClick(int year, int month, int day) {
                            edit_start.setText(year + "-" + getFormat(month) + "-" + getFormat(day));
                            popBean.startTime = edit_start.getText().toString();
                        }
                    })
                    .show();

        } else if (view.getId() == R.id.edit_end) {

            if (TextUtils.isEmpty(edit_start.getText().toString())) {
                ToastUtils.show("请先选择开始时间");
                return;
            }

            new ZDialogWheelDate(context).setTitle("选择结束时间")
                    .setDataSubmitListener(new ZDialogWheelDate.OnDateSubmitListener() {
                        @Override
                        public void onClick(int year, int month, int day) {

                            String selectTime = year + "-" + getFormat(month) + "-" + getFormat(day);

                            if (DateUtils.isDateOneBigger(edit_start.getText().toString(), selectTime)) {
                                ToastUtils.show("结束时间不能小于开始时间");
                                return;
                            }
                            edit_end.setText(selectTime);
                            popBean.endTime = edit_end.getText().toString();

                        }
                    })
                    .show();
        } else if (view.getId() == R.id.edit_start_t) {

            new ZDialogWheelDate(context).setTitle("选择开始时间")
                    .setDataSubmitListener(new ZDialogWheelDate.OnDateSubmitListener() {
                        @Override
                        public void onClick(int year, int month, int day) {
                            edit_start_t.setText(year + "-" + getFormat(month) + "-" + getFormat(day));
                            popBean.startAuditTime = edit_start_t.getText().toString();
                        }
                    })
                    .show();

        } else if (view.getId() == R.id.edit_end_t) {

            if (TextUtils.isEmpty(edit_start_t.getText().toString())) {
                ToastUtils.show("请先选择开始时间");
                return;
            }

            new ZDialogWheelDate(context).setTitle("选择结束时间")
                    .setDataSubmitListener(new ZDialogWheelDate.OnDateSubmitListener() {
                        @Override
                        public void onClick(int year, int month, int day) {

                            String selectTime = year + "-" + getFormat(month) + "-" + getFormat(day);

                            if (DateUtils.isDateOneBigger(edit_start_t.getText().toString(), selectTime)) {
                                ToastUtils.show("结束时间不能小于开始时间");
                                return;
                            }

                            edit_end_t.setText(selectTime);
                            popBean.endAuditTime = edit_end_t.getText().toString();

                        }
                    })
                    .show();
        } else if (view.getId() == R.id.tv_cancel) {
            this.dismiss();
        }
    }

    public interface EduScrennOnSubmitListener {
        void OnEduResSubmit(EduResScreenPopBean popBean);
    }

    private String getFormat(int num) {
        return String.format("%02d", num);
    }
}
