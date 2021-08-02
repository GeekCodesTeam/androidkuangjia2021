package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment;


import android.os.Bundle;
import android.view.View;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.BaseFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.adapter.ServiceTeamRecyclerViewAdapter;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.http.ServiceTeamListReply;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceTeamFragment extends BaseFragment {

    private String[] requestTag = new String[1];
    private ZRecyclerView mRecyclerView;
    private ServiceTeamRecyclerViewAdapter serviceTeamRecyclerViewAdapter;

    public ServiceTeamFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_service_team;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        initRecyclerView();
        mRecyclerView.refreshWithPull();
    }

    public void initRecyclerView() {

        mRecyclerView = getView(R.id.id_service_recyclerview);
        mRecyclerView.setGridLayout(true, 2);
        mRecyclerView.setIsLoadMoreEnabled(false);
        mRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {

            @Override
            public void onRefresh() {
                requestResources();
            }

            @Override
            public void onLoadMore() {}
        });

    }

    private void requestResources() {

        Map<String, String> map = new HashMap<String, String>();

        requestTag[0] = XCLHttp.postJson(XCLHttpUrlMaster.SERVICETEAM, map, new ZResponse<ServiceTeamListReply>(ServiceTeamListReply.class) {

            @Override
            public void onSuccess(Response response, final ServiceTeamListReply resObj) {
                try {
                    if (resObj.datalist != null && resObj != null && !resObj.datalist.isEmpty()) {
                        setDataToRecyclerView(resObj.datalist);
                    }else {
                        Toast.makeText(mActivity,"暂无数据",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }


    public void setDataToRecyclerView(List<ServiceTeamListReply.DatalistBean> list) {
        if (serviceTeamRecyclerViewAdapter == null) {
            serviceTeamRecyclerViewAdapter = new ServiceTeamRecyclerViewAdapter(mActivity);
            mRecyclerView.setAdapter(serviceTeamRecyclerViewAdapter);
        }

        mRecyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ServiceTeamListReply.DatalistBean>() {

            @Override
            public void onItemClick(View covertView, int position, ServiceTeamListReply.DatalistBean data) {

                ServiceTeamDialog infoDialog = new ServiceTeamDialog.Builder(mActivity)
                        .setIcon(data.img)
                        .setTitle(data.name)
                        .setMessage(data.introduction)
                        .setPostion(data.position)
                        .setTell(data.tell)
                        .setButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .create();
                infoDialog.show();
            }
        });

        if (list != null) {
            serviceTeamRecyclerViewAdapter.setDatas(list);
            if (list.size() < 10) {
                mRecyclerView.setNoMore(true);
                mRecyclerView.setIsShowNoMore(false);
            }else{
                mRecyclerView.setNoMore(false);
                mRecyclerView.setIsShowNoMore(true);
            }
            serviceTeamRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            serviceTeamRecyclerViewAdapter.setDatas(list);
            mRecyclerView.setNoMore(false);
            mRecyclerView.setIsShowNoMore(true);
            serviceTeamRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        XCLHttp.cancelRequest(requestTag);
        super.onDestroy();
    }

}
