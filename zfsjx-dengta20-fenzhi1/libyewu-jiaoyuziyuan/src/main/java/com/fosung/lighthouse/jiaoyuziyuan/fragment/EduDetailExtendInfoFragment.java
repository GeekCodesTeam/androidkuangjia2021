package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.fosung.eduapi.bean.EduResInfoBean;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.presenter.EduResExtendInfoPresenter;
import com.fosung.eduapi.view.EduResExtendInfoView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduDetailInfoAdapter;
import com.zcolin.frame.app.BaseFragment;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailExtendInfoFragment extends BaseFragment implements EduResExtendInfoView {

    private static final String ARG_RED_ID = "resourceId";
    private String resourceId;

    private EduDetailInfoAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private EduResExtendInfoPresenter eduResExtendInfoPresenter;

    @SuppressWarnings("unused")
    public static EduDetailExtendInfoFragment newInstance(String resourceId) {
        EduDetailExtendInfoFragment fragment = new EduDetailExtendInfoFragment();
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
        eduResExtendInfoPresenter = new EduResExtendInfoPresenter();
        eduResExtendInfoPresenter.onCreate(this);
    }

    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
        eduResExtendInfoPresenter.getEduResExtendInfoRequest(resourceId);
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_edu_detail_info_list;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        zRecyclerView = getView(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(mActivity)
                .inflate(R.layout.view_pullrecycler_empty, null));
        zRecyclerView.setIsProceeConflict(true);
    }


    @Override
    public void OnEduResExtendInfoSuccess(EduResInfoBean bean) {
        setDataToRecyclerView(bean.getDatalist());
    }

    @Override
    public void OnEduResExtendInfoFail(String msg) {

    }
    /**
     * 设置数据
     */
    public void setDataToRecyclerView(List<EduResInfoBean.DatalistBean> list) {

        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduDetailInfoAdapter(mActivity);
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        mRecyclerViewAdapter.setDatas(list);
    }

}