package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.os.Bundle;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;

@ActivityParam(isImmerse = false)
public class EduResourceDetailWebPreviewAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_resources_detail_web_preview);
        setToolbarTitle("审核详情");
    }

}