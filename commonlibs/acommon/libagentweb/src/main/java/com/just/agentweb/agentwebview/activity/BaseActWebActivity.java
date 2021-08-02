package com.just.agentweb.agentwebview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

//import com.geek.libutils.app.BaseAppManager;
import com.just.agentweb.R;
import com.just.agentweb.base.BaseAgentWebActivity;

public abstract class BaseActWebActivity extends BaseAgentWebActivity {
    public Activity activity;
    protected ImageView ivBack;
    protected TextView tvTitleName;
    protected TextView tv_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BaseAppManager.getInstance().add(this);
        setContentView(getLayoutId());
        activity = this;
        setup(savedInstanceState);
    }

    /*加载布局*/
    protected abstract int getLayoutId();


    /*设置属性*/
    protected void setup(@Nullable Bundle savedInstanceState) {
        findview();
        onclick();
    }

    private void findview() {
        ivBack = findViewById(R.id.baserecycleview_iv_back1);
        tvTitleName = findViewById(R.id.baserecycleview_tv_center_content1);
        tv_right = findViewById(R.id.baserecycleview_tv_right1);
    }

    private void onclick() {
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /*设置标题*/
    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        setTitleContent(title, "");
    }

    protected void setTitleContent(String title, String content) {
        if (!TextUtils.isEmpty(title)) {
            if (title.length() > 20) {
                title = title.substring(0, 20).concat("...");
            }
        }
        if (TextUtils.isEmpty(content)) {
            if (tvTitleName != null) {
                tvTitleName.setText(title);
            }
            return;
        }
        if (TextUtils.equals(title, content)) {
            if (tvTitleName != null) {
                tvTitleName.setText(title);
            }
        } else {
            if (tvTitleName != null) {
                tvTitleName.setText(content);
            }
        }
    }

    @Override
    protected void onDestroy() {
//        BaseAppManager.getInstance().remove(this);
        super.onDestroy();
    }
}
