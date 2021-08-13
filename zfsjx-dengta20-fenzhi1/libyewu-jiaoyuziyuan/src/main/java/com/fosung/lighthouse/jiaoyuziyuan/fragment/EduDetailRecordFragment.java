package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.fosung.eduapi.bean.EduResAuditRecordBean;
import com.fosung.eduapi.presenter.EduResAuditRecordPresenter;
import com.fosung.eduapi.view.EduResAuditRecordView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduDetailRecordAdapter;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.zcolin.frame.app.BaseFragment;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailRecordFragment extends BaseFragment implements EduResAuditRecordView {

    private static final String ARG_RED_ID = "resourceId";
    private String resourceId;
    private EduDetailRecordAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private EduResAuditRecordPresenter eduResAuditRecordPresenter;

    @SuppressWarnings("unused")
    public static EduDetailRecordFragment newInstance(String resourceId) {
        EduDetailRecordFragment fragment = new EduDetailRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RED_ID, resourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            resourceId = getArguments().getString(ARG_RED_ID);
        }
        eduResAuditRecordPresenter = new EduResAuditRecordPresenter();
        eduResAuditRecordPresenter.onCreate(this);
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_edu_detail_info_list;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        zRecyclerView = getView(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setIsLoadMoreEnabled(false);
        zRecyclerView.setIsRefreshEnabled(false);
        zRecyclerView.setEmptyView(LayoutInflater.from(mActivity)
                .inflate(R.layout.view_pullrecycler_empty, null));//setEmptyView
    }

    @Override
    protected void lazyLoad(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
        eduResAuditRecordPresenter.getEduReAuditRecordRequest(resourceId,(Objects.requireNonNull(CommonModel.Companion.get().getRolerBean())).manageId);
    }


    @Override
    public void OnEduResAuditRecordSuccess(EduResAuditRecordBean bean) {
        setDataToRecyclerView(bean.getDatalist());
    }

    @Override
    public void OnEduResAuditRecordFail(String msg) {
        setDataToRecyclerView(null);
        ToastUtil.toastShort(msg);
    }

    /**
     */
    public void setDataToRecyclerView(List<EduResAuditRecordBean.DatalistBean> list) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduDetailRecordAdapter();
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        mRecyclerViewAdapter.setDatas(list);
    }

}