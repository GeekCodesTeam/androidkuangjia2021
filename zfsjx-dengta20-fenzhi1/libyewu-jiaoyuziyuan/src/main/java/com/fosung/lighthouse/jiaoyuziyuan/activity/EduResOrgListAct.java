package com.fosung.lighthouse.jiaoyuziyuan.activity;

import static com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil.ACTION_SELECTED_EXECUTOR_CHANGED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.eduapi.presenter.EduResTerminalPresenter;
import com.fosung.eduapi.view.EduResTerminalView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourcesOrgAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResActivitysUtil;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.util.ActivityUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.List;
import java.util.Objects;

@ActivityParam(isImmerse = false)
public class EduResOrgListAct extends BaseActivity implements EduResTerminalView {
    private static final String ACTION_NAVIGATE_TO_EXECUTOR = "ACTION_NAVIGATE_TO_EXECUTOR";

    private EduResourcesOrgAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private EduResTerminalPresenter eduResTerminalPresenter;
    private TextView hasSelectTextView;
    private LocalBroadcastManager manager;
    private BroadcastReceiver broadcastReceiver;
    EduResSelectedExecutorsUtil util;

    private boolean isSingleSelect = false;//是否是单选， 默认多选
    private boolean canSelectDepartment = true;//是否能选中部门， 默认能选中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_detail_org_list);
        setToolbarTitle("选择报送终端");

        util = EduResSelectedExecutorsUtil.getInstance(this);
        EduResActivitysUtil.getInstance().addActivity(this);
        isSingleSelect = getIntent().getBundleExtra("bundle").getBoolean("isSingleSelect", false);
        canSelectDepartment = getIntent().getBundleExtra("bundle").getBoolean(
                "canSelectDepartment", true);
        hasSelectTextView = findViewById(R.id.hasSelectTextView);

        zRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(this)
                .inflate(R.layout.view_pullrecycler_empty, null));//setEmptyView

        eduResTerminalPresenter = new EduResTerminalPresenter();
        eduResTerminalPresenter.onCreate(this);
        eduResTerminalPresenter.getEduResTerminalRequest("org",null,(Objects.requireNonNull(CommonModel.Companion.get().getRolerBean())).manageId
                ,null);

        manager = LocalBroadcastManager.getInstance(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ACTION_SELECTED_EXECUTOR_CHANGED.equals(intent.getAction())){
                    hasSelectTextView.setText(util.getDataList().size() == 0 ? "未选择" : "已选择 " + util.getDataList().size() + " 人/部门");
                }

            }
        };
        manager.registerReceiver(broadcastReceiver, new IntentFilter(ACTION_NAVIGATE_TO_EXECUTOR));
        manager.registerReceiver(broadcastReceiver, new IntentFilter(ACTION_SELECTED_EXECUTOR_CHANGED));
        hasSelectTextView.setText(util.getDataList().size() == 0 ? "未选择" : "已选择 " + util.getDataList().size() + " 人/部门");
    }

    public void setDataToRecyclerView(List<EduResExecutorReplyBean.DataBean> list) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduResourcesOrgAdapter();
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }

        mRecyclerViewAdapter.setDatas(list);

        mRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<EduResExecutorReplyBean.DataBean>() {
            @Override
            public void onItemClick(View covertView, int position, EduResExecutorReplyBean.DataBean data) {
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.EduResSelectExecutorListAct");
                Bundle bundle = new Bundle();
                bundle.putParcelable("org", data);
                bundle.putBoolean("isSingleSelect", isSingleSelect);
                bundle.putBoolean("canSelectDepartment", canSelectDepartment);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    public void onHasSelectedViewClick(View view) {

        ActivityUtil.startActivity(this, EduResHasSelectedExecutorListAct.class);
    }

    @Override
    public void OnEduResTerminalSuccess(EduResExecutorReplyBean bean) {
        setDataToRecyclerView(bean.datalist);
    }

    @Override
    public void OnEduResTerminalFail(String msg) {

    }
}