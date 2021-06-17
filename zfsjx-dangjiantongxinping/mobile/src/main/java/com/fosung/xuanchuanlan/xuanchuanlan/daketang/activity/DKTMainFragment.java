package com.fosung.xuanchuanlan.xuanchuanlan.daketang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.BaseFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.adapter.DKTMainRecyclerViewAdapter;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity.DKTListContentApply;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

//import com.yuntongxun.ecsdk.Build;

public class DKTMainFragment extends BaseFragment {

    private ZRecyclerView mRecyclerView;
    private List userList = new ArrayList();//实体类
    private DKTMainRecyclerViewAdapter dktMainRecyclerViewAdapter;
    private int mPage = 0;//当前页
    private int mPageSize = 10;
    private String mId;
    private String[] requestTag = new String[1];

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_dktmain;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);

        Bundle bundle = this.getArguments();
        mId = bundle.getString("id");
        initRecyclerView();

    }


    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
        mRecyclerView.refreshWithPull();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void onDestroy() {
        XCLHttp.cancelRequest(requestTag);
        super.onDestroy();
    }

    public void initRecyclerView() {

        mRecyclerView = getView(R.id.dkt_pullLoadMoreRecyclerView);
        mRecyclerView.setGridLayout(true, 2);
        mRecyclerView.setIsLoadMoreEnabled(false);
        mRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {

            @Override
            public void onRefresh() {
                mPage = 0;
                requestResources(true);
            }

            @Override
            public void onLoadMore() {
//                mPage++;
//                requestResources(false);
            }
        });

    }

    /**
     * 分类专题
     */

    private void requestResources(final boolean isClear) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("id", mId);
        } catch (Exception e) {
        }

        requestTag[0] = XCLHttp.postJson(XCLHttpUrlMaster.DKTMAINTLIST, obj.toString(), new ZResponse<DKTListContentApply>(DKTListContentApply.class) {

            @Override
            public void onSuccess(Response response, DKTListContentApply resObj) {
                try {
                    if (resObj.datalist == null || resObj == null || resObj.datalist.isEmpty()) {
                        mRecyclerView.setNoMore(false);
                        mRecyclerView.setIsShowNoMore(true);
                        setDataToRecyclerView(null, isClear);
                        return;
                    }
                    if (mPage == 0)
                        setDataToRecyclerView(resObj.datalist, isClear);
                    else
                        setDataToRecyclerView(resObj.datalist, isClear);

                } catch (Exception e) {
                    e.printStackTrace();
                    setDataToRecyclerView(null, isClear);
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
                setDataToRecyclerView(null, isClear);
            }
        });

    }


    /**
     * @param isClear 是否清除原来的数据
     */
    public void setDataToRecyclerView(List<DKTListContentApply.DatalistBean> list, boolean isClear) {
        if (dktMainRecyclerViewAdapter == null) {
            dktMainRecyclerViewAdapter = new DKTMainRecyclerViewAdapter(mActivity);
            mRecyclerView.setAdapter(dktMainRecyclerViewAdapter);
        }

        mRecyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<DKTListContentApply.DatalistBean>() {

            @Override
            public void onItemClick(View covertView, int position, DKTListContentApply.DatalistBean data) {
                Intent intent = new Intent(mActivity, DKTTopicDetailActivity.class);
                intent.putExtra("topicId", data.id);
                intent.putExtra("name", data.name);
                intent.putExtra("pubTime", data.pubtime);
                intent.putExtra("coverImg", data.imgurl);
                intent.putExtra("desc", data.desc);
                startActivity(intent);
            }
        });

        if (list != null) {
            if (isClear) {
                dktMainRecyclerViewAdapter.setDatas(list);
            } else {
                dktMainRecyclerViewAdapter.addDatas(list);
            }
            if (list.size() < mPageSize) {
                mRecyclerView.setNoMore(true);
                mRecyclerView.setIsShowNoMore(false);
            } else {
                mRecyclerView.setNoMore(false);
                mRecyclerView.setIsShowNoMore(true);
            }
            dktMainRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            dktMainRecyclerViewAdapter.setDatas(list);
            mRecyclerView.setNoMore(false);
            mRecyclerView.setIsShowNoMore(true);
            dktMainRecyclerViewAdapter.notifyDataSetChanged();
        }

    }
}
