package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduDetailRecordAdapter;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailRecordFragment extends BaseFrameFrag {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private EduDetailRecordAdapter mRecyclerViewAdapter;
    private ZRecyclerView zRecyclerView;

    @SuppressWarnings("unused")
    public static EduDetailRecordFragment newInstance(int columnCount) {
        EduDetailRecordFragment fragment = new EduDetailRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_edu_detail_info_list;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        zRecyclerView = getView(R.id.pullLoadMoreRecyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        zRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        setDataToRecyclerView(datas,true);
    }

    /**
     * @param isClear 是否清除原来的数据
     */
    public void setDataToRecyclerView(List<String> list, boolean isClear) {
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new EduDetailRecordAdapter();
            zRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        mRecyclerViewAdapter.setDatas(list);
    }
}