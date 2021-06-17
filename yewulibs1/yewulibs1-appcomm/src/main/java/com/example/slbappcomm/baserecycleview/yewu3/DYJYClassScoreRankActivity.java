package com.example.slbappcomm.baserecycleview.yewu3;///*
// * *********************************************************
// *   author   colin
// *   company  fosung
// *   email    wanglin2046@126.com
// *   date     17-9-12 下午3:25
// * ********************************************************
// */
//
//package com.example.slbappcomm.baserecycleview.yewu3;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//
//import com.fosung.frameutils.http.ZHttp;
//import com.fosung.frameutils.http.response.ZResponse;
//import com.fosung.lighthouse.R;
//import com.fosung.lighthouse.common.base.BaseActivity;
//import com.fosung.lighthouse.common.consts.AppsConst;
//import com.fosung.lighthouse.dyjy.adapter.DYJYClassScoreRankAdapter;
//import com.fosung.lighthouse.dyjy.biz.DYJYApiMgr;
//import com.fosung.lighthouse.dyjy.http.entity.ClassScoreRankReply;
//import com.fosung.lighthouse.dyjy.http.entity.CourseResourceListReply;
//import com.fosung.lighthouse.master.biz.UserMgr;
//import com.zcolin.gui.zrecyclerview.ZRecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Response;
//
///**
// * 党员教育的网上班级的学时排行
// */
//public class DYJYClassScoreRankActivity extends BaseActivity {
//
//    private ArrayList<CourseResourceListReply.DataBean> listData = new ArrayList<>();
//    private ZRecyclerView zRecyclerView;
//    private DYJYClassScoreRankAdapter mRecyclerViewAdapter;
//    private int      mPage      = 1;                      //当前页
//    private String[] requestTag = new String[1];
//    private String classId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dyjy_class_score_rank);
//
//        setToolbarTitle("班级成员");
//
//        Intent intent = getIntent();
//        classId = intent.getStringExtra("classId");
//
//        initRes();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        zRecyclerView.setPullLoadMoreCompleted();
//    }
//
//    @Override
//    public void onDestroy() {
//        ZHttp.cancelRequest(requestTag);
//        super.onDestroy();
//    }
//
//    private void initRes() {
//        zRecyclerView = getView(R.id.pullLoadMoreRecyclerView);
//        zRecyclerView.setEmptyView(LayoutInflater.from(mActivity)
//                                                 .inflate(R.layout.view_pullrecycler_empty, null));//setEmptyView
//        zRecyclerView.setIsProceeConflict(true);
//        zRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {
//            public void onRefresh() {
//                mPage = 1;
//                zRecyclerView.setNoMore(false);
//                requestCourseResources(0);
//            }
//
//            @Override
//            public void onLoadMore() {
//                requestCourseResources(1);
//            }
//        });
//        zRecyclerView.refreshWithPull();
//    }
//
//
//    /**
//     * 从服务器拉取数据
//     *
//     * @param type 0 下拉刷新  1加载更多
//     */
//    public void requestCourseResources(final int type) {
//        requestTag[0] = DYJYApiMgr.getClassScoreRankList(mPage, AppsConst.PAGE_SIZE, UserMgr.getUserName(), classId, new ZResponse<ClassScoreRankReply>
//                (ClassScoreRankReply.class) {
//            @Override
//            public void onSuccess(Response response, ClassScoreRankReply resObj) {
//                try {
//                    if (resObj != null && resObj.list != null && resObj.list.size() != 0) {
//                        setDataToRecyclerView(resObj.list, type == 0);
//                        if (mRecyclerViewAdapter.getItemCount() < (AppsConst.PAGE_SIZE) * mPage && mRecyclerViewAdapter.getItemCount() != 0) {
//                            zRecyclerView.setNoMore(true);
//                        } else {
//                            mPage++;
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int code, String error) {
//                if (code != 204) {
//                    super.onError(code, error);
//                }
//                setDataToRecyclerView(null, type == 0);
//            }
//
//            @Override
//            public void onFinished() {
//                super.onFinished();
//                zRecyclerView.setPullLoadMoreCompleted();
//            }
//        });
//    }
//
//    /**
//     * @param isClear 是否清除原来的数据
//     */
//    public void setDataToRecyclerView(List<ClassScoreRankReply.ListBean> list, boolean isClear) {
//        if (mRecyclerViewAdapter == null) {
//            mRecyclerViewAdapter = new DYJYClassScoreRankAdapter(false);
//            zRecyclerView.setAdapter(mRecyclerViewAdapter);
//        }
//
//        if (isClear) {
//            mRecyclerViewAdapter.setDatas(list);
//        } else {
//            mRecyclerViewAdapter.addDatas(list);
//        }
//    }
//
//
//}
