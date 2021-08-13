package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.eduapi.bean.EduResourceListBean;
import com.fosung.eduapi.presenter.EduResPresenter;
import com.fosung.eduapi.view.EduResListView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourcesListAdapter;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.geek.libutils.device.DvAppUtil;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.List;
import java.util.Objects;

/**
 * @author fosung
 */
@ActivityParam(isImmerse = false)
public class EduResSearchListAct extends BaseActivity implements EduResListView {

    private EduResourcesListAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;
    private EditText edit_query;
    private TextView tv_cancel;
    private EduResPresenter eduResPresenter;
    private String auditStatus;
    private String searchText;
    private int mPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_res_search_list);
        setToolbarTitle("搜索");

        auditStatus = getIntent().getStringExtra("auditStatus");
        searchText = getIntent().getStringExtra("text");

        edit_query = findViewById(R.id.edit_query);
        edit_query.setText(searchText);
        edit_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchText = edit_query.getText().toString();
                    zRecyclerView.refreshWithPull();
                    return true;
                }
                return false;
            }
        });
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DvAppUtil.closeSoftInput(mActivity);
            }
        });
        zRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        zRecyclerView.setEmptyView(LayoutInflater.from(this)
                .inflate(R.layout.view_pullrecycler_empty, null));

        eduResPresenter = new EduResPresenter();
        eduResPresenter.onCreate(this);

        initPullLoad();

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
                null,
                null,
                searchText,
                null,
                null,
                null,
                null,
                null,
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