package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.frame.app.BaseFrameFrag;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailReviewFragment extends BaseFrameFrag {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    @SuppressWarnings("unused")
    public static EduDetailReviewFragment newInstance(int columnCount) {
        EduDetailReviewFragment fragment = new EduDetailReviewFragment();
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
        return R.layout.fragment_edu_detail_review;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);

    }
}