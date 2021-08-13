package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.util.EduResSelectedExecutorsUtil;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;


public class EduResHasSelectedExecutorListAct extends BaseActivity {


    EduResSelectedExecutorsUtil util;
    ZRecyclerView zRecyclerView;
    BaseRecyclerAdapter<EduResExecutorReplyBean.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_has_selected_executor_list);
        setToolbarTitle("选择报送终端");
        util = EduResSelectedExecutorsUtil.getInstance(this);
        zRecyclerView = findViewById(R.id.recyclerView);
        zRecyclerView.addDefaultItemDecoration();
        adapter = new BaseRecyclerAdapter<EduResExecutorReplyBean.DataBean>() {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_edu_has_selected_executor;
            }

            @Override
            public void setUpData(BaseRecyclerAdapter.CommonHolder holder, int position, int viewType, EduResExecutorReplyBean.DataBean data) {
                TextView executorNameTextView = getView(holder, R.id.executorNameTextView);
                executorNameTextView.setText(data.name);
                TextView closeTextView = getView(holder,R.id.closeTextView);
                closeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.checked = false;
                        int index = adapter.getDatas().indexOf(data);
                        adapter.getDatas().remove(data);
//                        adapter.notifyDataSetChanged();
                        adapter.notifyItemRemoved(index);
                        util.remove(data);

                    }
                });
            }
        };
        zRecyclerView.setAdapter(adapter);

//        Collections.sort(util.getDataList(), new Comparator< GZWXExecutorReplyBean.DataBean.UserListBean>() {
//            @Override
//            public int compare( GZWXExecutorReplyBean.DataBean.UserListBean o1,  GZWXExecutorReplyBean.DataBean.UserListBean o2) {
//                return o1.getType() - o2.getType();
//            }
//        });
        adapter.setDatas(util.getDataList());


    }
}
